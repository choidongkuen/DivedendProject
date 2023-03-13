package com.example.dividendproject.web;

import com.example.dividendproject.dto.Company;
import com.example.dividendproject.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<List<String>> autoComplete(@RequestParam String keyword) {
        return ResponseEntity.ok().body(companyService.autoComplete(keyword));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(
            @RequestParam(name = "page") final int page,
            @RequestParam(name = "size") final int size) {

        return new ResponseEntity<>(
                companyService.getAllCompanies(PageRequest.of(page, size)), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Company> addCompany(@RequestBody Company request) {

        String ticker = request.getTicker().trim();

        if (ObjectUtils.isEmpty(ticker)) {
            log.error("ticker is Empty Error!");
            throw new RuntimeException("ticker is Empty");
        }

        Company company = this.companyService.save(ticker); // ticker 를 통해 Company 객체 생성
        this.companyService.addAutoCompleteKeyword(company.getName()); // trie 에 회사명 저장
        return new ResponseEntity<>(company, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
