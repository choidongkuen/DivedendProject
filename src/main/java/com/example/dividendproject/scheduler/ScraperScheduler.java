package com.example.dividendproject.scheduler;

import com.example.dividendproject.domain.entity.CompanyEntity;
import com.example.dividendproject.domain.entity.DividendEntity;
import com.example.dividendproject.domain.repository.CompanyRepository;
import com.example.dividendproject.domain.repository.DividendRepository;
import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.ScrapedResult;
import com.example.dividendproject.scraper.YahooFinancialScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ScraperScheduler {

    private final CompanyRepository companyRepository;

    private final DividendRepository dividendRepository;

    private final YahooFinancialScraper scraper;


    @Scheduled(cron = "0 0 1 * * *") // 매일 오전 1시마다 스케쥴러
    public void yahooFinanceScheduler() {

        log.info("scraping scheduling starts!");

        // 1. 데이터베이스의 회사 정보를 모두 읽은 후
        List<CompanyEntity> companyList = companyRepository.findAll();

        // 2. 회사에 해당하는 배당금 정보를 스크래핑
        for (CompanyEntity companyEntity : companyList) {
            log.info("scraping scheduler starts ->" + companyEntity.getName());
            ScrapedResult result = scraper.scrap(Company.fromEntity(companyEntity)); // 회사에 해당하는 배당금 정보

            // companyRepository 에 저장된 회사 갯수 만큼 scraper 를 요청하므로 서버에 부하 -> Thread

            // 3. 스크래핑 결과 중 배당금 데이터베이스에 존재하지 않는 배당금 정보 저장
            result.getDividendEntities().stream()
                  .map(d -> new DividendEntity(companyEntity.getId(), d))
                  .forEach(e -> {
                      if (!dividendRepository.existsByCompanyIdAndDate(e.getCompanyId(), e.getDate())) {
                          dividendRepository.save(e);
                      }

                  });

            try {
                Thread.sleep(1000);
                log.info("scraping scheduler normally ends");
            } catch (InterruptedException e) {
                log.error("Scraper Scheduling errors!");
                Thread.currentThread().interrupt();
            }
        }
    }
}
