package com.gestionapprovisionnements.smartshop.dto.Order.response;

import com.gestionapprovisionnements.smartshop.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private Long clientId;
    private List<OrderItemResponse> items;
    private Double sousTotal;
    private Double remise;
    private Double tva;
    private Double total;
    private String codePromo;
    private OrderStatus statut;
    private Double montantRestant;
    private LocalDateTime dateCommande;
    private String message; // optional rejection message
}
