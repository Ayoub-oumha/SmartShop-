package com.gestionapprovisionnements.smartshop.dto.Order.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long clientId;
    private List<OrderItemRequest> items;
    private String promoCode; // optional
}
