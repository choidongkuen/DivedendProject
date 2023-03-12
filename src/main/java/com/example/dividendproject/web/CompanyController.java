package com.example.dividendproject.web;

import com.example.dividendproject.dto.Company;
import com.example.dividendproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/autocomplete") // 키워드에 해당하는 회사 ticker 조회
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Company>> searchCompany() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) {

        String ticker = request.getTicker().trim();

        if(ObjectUtils.isEmpty(ticker)){
            log.error("ticker is Empty Error!");
            throw new RuntimeException("ticker is Empty");
        }

        Company company = companyService.save(ticker);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
