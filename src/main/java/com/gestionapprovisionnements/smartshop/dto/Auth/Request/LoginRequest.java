package com.gestionapprovisionnements.smartshop.dto.Auth.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username required")
    private String username;
    @NotBlank
    @Size(min = 6 , message = "password must be at least 6 carachter")
    private String password;

}
