package com.example.dividendproject.web;


import com.example.dividendproject.domain.entity.MemberEntity;
import com.example.dividendproject.dto.Auth;
import com.example.dividendproject.security.TokenProvider;
import com.example.dividendproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final MemberService memberService;

    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.Signup request) {
        // 회원가입을 위한 컨트롤러
        return ResponseEntity.ok().body(this.memberService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.Signin request) {
        MemberEntity member = this.memberService.authenticate(request);

        String token = this.tokenProvider.generateToken(member.getEmail(), member.getAuthority());

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization",token);
        return new ResponseEntity<>(member,header,HttpStatus.OK);
    }
}
