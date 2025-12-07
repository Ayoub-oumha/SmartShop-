package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.entity.CodePromo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {
    Optional<CodePromo> findByCode(String code);
}
