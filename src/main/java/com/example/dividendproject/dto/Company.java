package com.example.dividendproject.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    private String name;

    private String ticker;
}
