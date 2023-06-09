package com.example.dividendproject.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {

    ROLE_MEMBER("일반 회원"),
    ROLE_ADMIN("관리자");

    private final String state;
}
