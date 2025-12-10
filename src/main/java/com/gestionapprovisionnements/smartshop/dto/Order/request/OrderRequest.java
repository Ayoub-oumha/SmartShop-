package com.gestionapprovisionnements.smartshop.dto.Order.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "client id is required")
    private Long clientId;
    @NotNull(message = "items or product are required")
    private List<OrderItemRequest> items;
    private String promoCode;
}
