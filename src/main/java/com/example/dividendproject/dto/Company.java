package com.example.dividendproject.dto;

import com.example.dividendproject.domain.entity.CompanyEntity;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String ticker;
    private String name;


    public static Company fromEntity(CompanyEntity companyEntity) {
        return Company.builder()
                .name(companyEntity.getName())
                .ticker(companyEntity.getTicker())
                .build();
    }
}
