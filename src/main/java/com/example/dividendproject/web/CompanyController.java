package com.example.dividendproject.web;

import com.example.dividendproject.dto.Company;
import com.example.dividendproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<?> searchCompany() {

        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(@RequestParam Company request) {
        String ticker = request.getTicker();

        if(ticker == null) {
            throw new RuntimeException();
        }

        Company company = companyService.save(ticker);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
