package com.example.dividendproject.service;

import com.example.dividendproject.domain.entity.MemberEntity;
import com.example.dividendproject.domain.repository.MemberRepository;
import com.example.dividendproject.dto.Auth;
import com.example.dividendproject.exception.AlreadyMemberSignupException;
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
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return this.memberRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원 정보가 존재하지 않습니다."));
    }

    public MemberEntity signUp(Auth.Signup signup) {

        this.memberRepository.findByUserName(signup.getUserName())
                             .orElseThrow(() -> new AlreadyMemberSignupException("이미 사용중인 회원입니다."));

        return this.memberRepository.save(MemberEntity.builder()
                                               .userName(signup.getUserName())
                                               .password(passwordEncoder.encode(signup.getPassword()))
                                               .authority(signup.getAuthority())
                                               .build()
        );

    }

    public MemberEntity authenticate(Auth.Signin signin) {
        return null;
    }
}
