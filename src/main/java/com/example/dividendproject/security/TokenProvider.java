package com.example.dividendproject.security;

import com.example.dividendproject.domain.constant.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.springframework.util.StringUtils.hasText;


@Component
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간 만료 기간
    private static final String KEY_ROLES = "roles";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String email, Authority authority) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put(KEY_ROLES, authority.getState());


        return Jwts.builder()
                   .setClaims(claims)
                   .setIssuedAt(new Date()) // 발행 시간
                   .setExpiration(new Date(new Date().getTime() + TOKEN_EXPIRE_TIME)) // 만료 시간
                   .signWith(SignatureAlgorithm.HS512, secretKey)
                   .compact();
    }

    public boolean validateToken(String token) {

        if (!hasText(token)) {
            return false;
        }

        return parseClaims(token).getExpiration().before(new Date());
    }

    public String getUserEmail(String token) {
        return this.parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {

        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();

        }

    }

}
