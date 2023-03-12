package com.example.dividendproject.service;


import com.example.dividendproject.domain.entity.CompanyEntity;
import com.example.dividendproject.domain.entity.DividendEntity;
import com.example.dividendproject.domain.repository.CompanyRepository;
import com.example.dividendproject.domain.repository.DividendRepository;
import com.example.dividendproject.dto.Company;
import com.example.dividendproject.dto.Dividend;
import com.example.dividendproject.dto.ScrapedResult;
import com.example.dividendproject.exception.NotFoundCompanyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class FinanceService {

    private final CompanyRepository companyRepository;

    private final DividendRepository dividendRepository;

    @Transactional(readOnly = true)
    public ScrapedResult getAllDividendsByCompanyName(String companyName) {


        // 1. 회사명을 기준으로 회사 정보를 조회
        CompanyEntity companyEntity = companyRepository.findByName(companyName)
                     .orElseThrow(() -> new NotFoundCompanyException("일치하는 회사 정보가 존재하지 않습니다."));

        // 2. 조회된 회사 ID 로 배당금 정보 조회
        List<DividendEntity> dividendEntities = dividendRepository.findByCompanyId(companyEntity.getId());


        // 3. 결과 조합 후 반환
        return ScrapedResult.builder()
                .company(Company.fromEntity(companyEntity))
                .dividendEntities(dividendEntities.stream()
                        .map(Dividend::fromEntity)
                        .collect(Collectors.toUnmodifiableList()))
                .build();

    }
}
