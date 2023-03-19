package com.example.dividendproject.service;

import com.example.dividendproject.domain.entity.CompanyEntity;
import com.example.dividendproject.domain.entity.DividendEntity;
import com.example.dividendproject.domain.repository.CompanyRepository;
import com.example.dividendproject.domain.repository.DividendRepository;
import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.ScrapedResult;
import com.example.dividendproject.exception.AlreadyCompanyExistsException;
import com.example.dividendproject.exception.NotFoundCompanyException;
import com.example.dividendproject.scraper.YahooFinancialScraper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyService {

    private final Trie<String,String> trie;

    private final YahooFinancialScraper scraper;

    private final CompanyRepository companyRepository;

    private final DividendRepository dividendRepository;


    @Transactional
    public Company save(String ticker) {

        if (companyRepository.existsByTicker(ticker)) {
            throw new AlreadyCompanyExistsException("already exists ticker-> " + ticker);
        }

        return this.storeCompanyAndDividend(ticker);
    }

    private Company storeCompanyAndDividend(String ticker) {

        // ticker 를 기준으로 Company 정보 스크래핑
        Company company = scraper.scrapCompanyByTicker(ticker);

        if (company == null) {
            throw new NotFoundCompanyException("failed to scrap ticker -> " + ticker);
        }

        // 해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = scraper.scrap(company);

        // 스크래핑 결과 저장
        companyRepository.save(new CompanyEntity(company)); // 회사 정보 저장

        // 배당금 정보 저장
        dividendRepository.saveAll(scrapedResult.getDividendEntities().stream()
                        .map(d -> new DividendEntity(new CompanyEntity(company).getId(),d))
                        .collect(Collectors.toList()));
        return company;
    }

    @Transactional(readOnly = true)
    public List<Company> getAllCompanies(Pageable pageable) {

        return companyRepository.findAll(pageable).getContent()
                                .stream()
                                .map(Company::fromEntity)
                                .collect(Collectors.toList());

    }

    @Transactional
    public void addAutoCompleteKeyword(String keyword) { // keyword 추가

        this.trie.put(keyword, null);

    }

    @Transactional
    public void deleteAutoCompleteKeyword(String keyword) { // keyword 삭제

        this.trie.remove(keyword);
    }

    @Transactional
    public List<String> autoComplete(String keyword) { // keyword 를 통해 검색
        return new ArrayList<>(this.trie.prefixMap(keyword).keySet());
    }

    @Transactional(readOnly = true)
    public List<String> getCompanyNamesByKeyword(String keyword) {

        return this.companyRepository.findByNameStartingWithIgnoreCase(keyword)
                .stream()
                .map(d -> d.getName())
                .collect(Collectors.toList());
    }
}
