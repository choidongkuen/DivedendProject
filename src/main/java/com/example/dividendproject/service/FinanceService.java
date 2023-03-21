package com.example.dividendproject.service;


import com.example.dividendproject.domain.constant.CacheKey;
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
import org.springframework.cache.annotation.Cacheable;
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

    // 동일한 요청이 자주 들어오는가? -> (규모가 크지 않은 회사라면) Yes
    // 변경이 빈번한가? -> No

    @Cacheable(key = "#companyName", value = CacheKey.KEY_FINANCE_KEY)
    @Transactional(readOnly = true)
    public ScrapedResult getAllDividendsByCompanyName(String companyName) {

        // @Cacheable 은 캐시에 데이터가 있을 경우 해당 메소드 실행 하지 않는 특성을 가짐.
        // 만약 Redis Cache 에 해당 회사에 대한 배당금 정보가 없다면 데이터베이스에서 조회할 것이고 로그가 찍힘.
        log.info("search company -> " + companyName);

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
