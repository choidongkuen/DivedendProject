package com.example.dividendproject.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DIVIDEND")
public class DividendEntity {

    @Id
    private Long id;

    private Long companyId;

    private LocalDateTime date;

    private String dividend;
}
