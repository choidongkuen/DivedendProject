package com.example.dividendproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dividend {

    private LocalDateTime date;

    private String dividend;
}
