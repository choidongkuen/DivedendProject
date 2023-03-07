package com.example.dividendproject.service;

import com.example.dividendproject.domain.entity.CompanyEntity;
import com.example.dividendproject.domain.entity.DividendEntity;
import com.example.dividendproject.domain.repository.CompanyRepository;
import com.example.dividendproject.domain.repository.DividendRepository;
import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.ScrapedResult;
import com.example.dividendproject.scraper.YahooFinancialScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

    private final YahooFinancialScraper scraper;

    private final CompanyRepository companyRepository;

    private final DividendRepository dividendRepository;


    @Transactional
    public Company save(String ticker) {

        if(companyRepository.existByTicker(ticker)) {
            throw new RuntimeException("already exists ticker-> " + ticker);
        }

        return this.storeCompanyAndDividend(ticker);
    }

    @Transactional
    public Company storeCompanyAndDividend(String ticker) {

        // ticker 를 기준으로 Company 정보 스크래핑
        Company company = scraper.scrapCompanyByTicker(ticker);

        if (company == null) {
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = scraper.scrap(company);

        // 스크래핑 결과 저장
        CompanyEntity companyEntity = new CompanyEntity(company);
        companyRepository.save(companyEntity); // 회사 정보 저장

        List<DividendEntity> dividendEntities
                = scrapedResult.getDividendEntities().stream()
                               .map(d -> dividendRepository.save(new DividendEntity(companyEntity.getId(), d)))
                               .collect(Collectors.toList()); // 배당금 정보 저장

        dividendRepository.saveAll(dividendEntities);
        return company;
    }

}
