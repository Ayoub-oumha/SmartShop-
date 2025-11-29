package com.gestionapprovisionnements.smartshop.repository;

import com.gestionapprovisionnements.smartshop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
