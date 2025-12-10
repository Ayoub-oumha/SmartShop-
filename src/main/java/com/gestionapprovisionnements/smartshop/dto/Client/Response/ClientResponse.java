package com.gestionapprovisionnements.smartshop.dto.Client.Response;

import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientResponse {
    private Long id;
    private String nom;
    private String email;
    private int totalOrders;
    private double totalSpent;
    private LocalDateTime firstOrderDate;
    private LocalDateTime lastOrderDate;
    private CustomerTier customerTier;
}
