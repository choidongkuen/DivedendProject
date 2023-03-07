package com.example.dividendproject.web;

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

    @GetMapping("/dividend/{companyName}") // 회사에 해당하는 배당금 정보 조회
    public ResponseEntity<?> searchFinanceResponseEntity(
            @PathVariable String companyName) {

        return null;
    }
}