package com.example.dividendproject.dto;

import com.example.dividendproject.domain.constant.Authority;
import com.example.dividendproject.domain.entity.MemberEntity;
import lombok.*;

public class Auth {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signin { // 로그인

        private String userName;

        private String password;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signup { // 회원 가입

        private String userName;

        private String password;

        private Authority authority;
    }
}
