package com.gestionapprovisionnements.smartshop.dto.Payment.request;

import com.gestionapprovisionnements.smartshop.entity.enums.PaymentType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateDTO {
    @NotNull
    private Long orderId;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double montant;

    private PaymentType type;
    private LocalDateTime dateEncaissement; // optional
    private String numeroCheque;
    private String banque;
    private LocalDateTime echeance;
    private String reference;
}

