package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);
}
