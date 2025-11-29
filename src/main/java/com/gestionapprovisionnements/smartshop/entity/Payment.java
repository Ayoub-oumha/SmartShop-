package com.gestionapprovisionnements.smartshop.entity;

import com.gestionapprovisionnements.smartshop.entity.enums.PaymentStatus;
import com.gestionapprovisionnements.smartshop.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long orderId;
    
    @Column(nullable = false)
    private Integer numeroPaiement;
    
    @Column(nullable = false)
    private Double montant;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
    
    @Column(nullable = false)
    private LocalDateTime datePaiement = LocalDateTime.now();
    
    private LocalDateTime dateEncaissement;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus statut = PaymentStatus.EN_ATTENTE;
    
    private String numeroCheque;
    private String banque;
    private LocalDateTime echeance;
    private String reference;
}
