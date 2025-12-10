package com.gestionapprovisionnements.smartshop.dto.Product.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdatRequest {
    private String name;


    private Double price;

    private Integer stock;
}

