package com.gestionapprovisionnements.smartshop.dto.Payment.request;

import com.gestionapprovisionnements.smartshop.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Double montant;
    private PaymentType type;
    private LocalDateTime dateEncaissement; // optional for deferred payments
    private String numeroCheque;
    private String banque;
    private LocalDateTime echeance;
    private String reference;
}

