package com.example.dividendproject.dto;

import com.example.dividendproject.domain.entity.DividendEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dividend {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;

    private String dividend;

    public static Dividend fromEntity(DividendEntity dividendEntity) {
        return Dividend.builder()
                .date(dividendEntity.getDate())
                .dividend(dividendEntity.getDividend())
                .build();
    }
}
