package com.example.dividendproject.domain.entity;


import com.example.dividendproject.dto.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "COMPANY")
public class CompanyEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TICKER")
    private String ticker;

    @Column(name = "COMPANY_NAME")
    private String name;

    public CompanyEntity(Company company) {
        this.ticker = company.getTicker();
        this.name = company.getName();
    }

}