package com.example.dividendproject.dto;

import lombok.*;

public class Auth {

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signin { // 로그인

        private String email;

        private String password;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signup { // 회원 가입

        private String name;

        private String email;

        private String password;

        private String authority;
    }
}
