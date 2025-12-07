package com.gestionapprovisionnements.smartshop.dto.Payment.response;

import com.gestionapprovisionnements.smartshop.entity.enums.PaymentStatus;
import com.gestionapprovisionnements.smartshop.entity.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private Integer numeroPaiement;
    private Double montant;
    private PaymentType type;
    private LocalDateTime datePaiement;
    private LocalDateTime dateEncaissement;
    private PaymentStatus statut;
    private String numeroCheque;
    private String banque;
    private LocalDateTime echeance;
    private String reference;
}

