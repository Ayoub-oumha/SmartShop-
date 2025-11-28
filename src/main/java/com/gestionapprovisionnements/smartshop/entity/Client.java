package com.gestionapprovisionnements.smartshop.entity;

import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "clients")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    private int totalOrders = 0;
    private double totalSpent = 0.0;

    private LocalDateTime firstOrderDate;
    private LocalDateTime lastOrderDate;

    @Enumerated(EnumType.STRING)
    private CustomerTier tier = CustomerTier.BASIC;
}
