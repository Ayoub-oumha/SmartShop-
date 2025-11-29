package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
