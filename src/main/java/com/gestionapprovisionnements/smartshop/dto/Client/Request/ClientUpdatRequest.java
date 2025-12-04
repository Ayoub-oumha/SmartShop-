package com.gestionapprovisionnements.smartshop.dto.Client.Request;

import com.gestionapprovisionnements.smartshop.entity.enums.CustomerTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdatRequest {
    private String nom;
    @Email
    private String email;
    private CustomerTier customerTier ;
}
