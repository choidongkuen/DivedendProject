package com.example.dividendproject.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 하나의 회원의 요청마다 Jwt 유효성을 검증하기 위한 필터
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 컨트롤러를 거치기 전 필터를 먼저 거침

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";

    private final TokenProvider tokenProvider;

    // JWT => Authorization: "Bearer dasxcaewxacsdsadwxsad"
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = this.resolveTokenFromToken(request);

        if (token != null && this.tokenProvider.validateToken(token)) {
            // 토큰 유효성 검증
            Authentication auth = this.tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);  // JWT 유효성 검증 후, 인증정보를 ContextHolder 에 담기
        }
        filterChain.doFilter(request, response); // Spring SecurityFilterChain 으로 동작
    }

    private String resolveTokenFromToken(HttpServletRequest request) { // 토큰 정보를 가져오는 메소드

        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
