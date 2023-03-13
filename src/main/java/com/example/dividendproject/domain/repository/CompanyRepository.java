package com.example.dividendproject.domain.repository;

import com.example.dividendproject.domain.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    boolean existsByTicker(String ticker);

    Page<CompanyEntity> findAll(Pageable pageable);

    Optional<CompanyEntity> findByName(String companyName);

    List<CompanyEntity> findByNameStartingWithIgnoreCase(String keyword);

}
