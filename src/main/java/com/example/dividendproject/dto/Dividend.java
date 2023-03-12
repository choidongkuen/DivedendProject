package com.example.dividendproject.dto;

import com.example.dividendproject.domain.entity.DividendEntity;
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

    public static Dividend fromEntity(DividendEntity dividendEntity) {
        return Dividend.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
