package com.example.dividendproject.domain.repository;

import com.example.dividendproject.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email); // 회원 이메일을 통해 회원 엔티티를 찾는 메소드

    boolean existsByEmail(String email); // 회원가입을 할때 이미 존재하는 회원인지 확인하기 위한 메소드
}
