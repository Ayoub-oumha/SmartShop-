package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
}
