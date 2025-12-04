package com.gestionapprovisionnements.smartshop.dto.Product.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String name;


    @NotNull(message = "Le prix unitaire est obligatoire")
    @Min(value = 0, message = "Le prix unitaire doit être positif")
    private Double price;

    @NotNull(message = "Le stock disponible est obligatoire")
    @Min(value = 0, message = "Le stock disponible doit être positif")
    private Integer stock;
}
