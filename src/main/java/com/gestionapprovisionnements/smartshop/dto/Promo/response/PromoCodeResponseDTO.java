package com.gestionapprovisionnements.smartshop.dto.Promo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeResponseDTO {
    private Long id;
    private String code;
    private Double pourcentageRemise;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private Boolean actif;
}

