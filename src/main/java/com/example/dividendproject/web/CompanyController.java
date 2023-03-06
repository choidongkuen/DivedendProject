package com.example.dividendproject.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    @GetMapping("/autocomplete") // 키워드에 해당하는 회사 ticker 조회
    public ResponseEntity<?> autoComplete(@RequestParam String keyword) {
        return null;
    }

    @GetMapping()
    public ResponseEntity<?> searchCompany() {

        return null;
    }

    @PostMapping()
    public ResponseEntity<?> addCompany() {

        return null;
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
