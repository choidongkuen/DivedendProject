package com.example.dividendproject.domain.repository;

import com.example.dividendproject.domain.entity.DividendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<DividendEntity, Long> {
    List<DividendEntity> findByCompanyId(Long id);
}
