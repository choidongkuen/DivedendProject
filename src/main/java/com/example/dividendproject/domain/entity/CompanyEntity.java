package com.example.dividendproject.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "COMPANY")
public class CompanyEntity{

    @Id
    private Long id;

    @Column(unique = true)
    private String ticker;

    private String name;

}