package com.gestionapprovisionnements.smartshop.dto.Client.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @Email
    @NotBlank
    private String email;
}
