package com.example.dividendproject.web;

import com.example.dividendproject.dto.ScrapedResult;
import com.example.dividendproject.service.FinanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/finance")
@RestController
public class FinanceController {

    private final FinanceService financeService;
    @GetMapping("/dividend/{companyName}") // 회사에 해당하는 배당금 정보 조회
    public ResponseEntity<ScrapedResult> searchFinanceResponseEntity(
            @PathVariable(name = "companyName") String companyName) {

        return ResponseEntity.ok(financeService.getAllDividendsByCompanyName(companyName));
    }
}
