package com.example.dividendproject.dto;

import com.example.dividendproject.domain.entity.MemberEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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

        private String roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .userName(this.name)
                    .email(this.email)
                    .password(this.password)
                    .role(this.roles)
                    .build();
        }
    }
}
