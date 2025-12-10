package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.controller.PromoCode;
import com.gestionapprovisionnements.smartshop.entity.CodePromo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodePromoRepository extends JpaRepository<CodePromo, Long> {
    Optional<CodePromo> findByCode(String code);
    Page<CodePromo> findAll (Pageable pageable) ;
}
