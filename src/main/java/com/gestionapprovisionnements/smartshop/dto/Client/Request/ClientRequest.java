package com.gestionapprovisionnements.smartshop.dto.Client.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @Email
    @NotBlank(message = "email est obligatoire")
    private String email;
}
