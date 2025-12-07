package com.gestionapprovisionnements.smartshop.dto.Payment.request;

import com.gestionapprovisionnements.smartshop.entity.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUpdateStatusDTO {
    @NotNull
    private PaymentStatus statut;
    private LocalDateTime dateEncaissement; // optional, used when setting ENCAISSE
    private String reason; // optional for rejected
}

