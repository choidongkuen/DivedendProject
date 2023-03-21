package com.example.dividendproject.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Authority {

    MEMBER("일반 회원"),
    ADMIN("관리자");

    private final String state;
}
