package com.gestionapprovisionnements.smartshop.dto.Product.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String nom;
    private Double prixUnitaire;
    private Integer stockDisponible;
}

