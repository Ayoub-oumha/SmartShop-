package com.gestionapprovisionnements.smartshop.dto.Auth.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    @Size(min = 6 )
    private String password;

}
