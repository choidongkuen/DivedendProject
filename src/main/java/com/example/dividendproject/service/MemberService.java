package com.example.dividendproject.service;

import com.example.dividendproject.domain.entity.MemberEntity;
import com.example.dividendproject.domain.repository.MemberRepository;
import com.example.dividendproject.dto.Auth;
import com.example.dividendproject.exception.AlreadyMemberSignupException;
import com.example.dividendproject.exception.NotMatchPasswordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // MemberEntity UserDetails 인터페이스 구현
        return this.memberRepository.findByEmail(email)
                                    .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원 정보가 존재하지 않습니다."));
    }

    public MemberEntity signUp(Auth.Signup signup) { // 회원가입

        boolean exists = this.memberRepository.existsByEmail(signup.getEmail());

        if (exists) {
            throw new AlreadyMemberSignupException("이미 사용중인 회원입니다.");
        }

        return this.memberRepository.save(MemberEntity.builder()
                                                      .email(signup.getEmail())
                                                      .userName(signup.getName())
                                                      .password(passwordEncoder.encode(signup.getPassword()))
                                                      .roles(signup.getRoles())
                                                      .build()
        );

    }

    public MemberEntity authenticate(Auth.Signin signin) { // 로그인시 회원 인증

        MemberEntity memberEntity = this.memberRepository.findByEmail(signin.getEmail())
                                                         .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원 정보가 존재하지 않습니다."));

        if (!this.passwordEncoder.matches(signin.getPassword(), memberEntity.getPassword())) {
            log.error(memberEntity.getPassword() + "!!");
            log.error(this.passwordEncoder.encode(signin.getPassword()) + "!!");
            throw new NotMatchPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return memberEntity;
    }
}
