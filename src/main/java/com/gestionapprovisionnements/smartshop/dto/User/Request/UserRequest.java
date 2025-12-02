package com.gestionapprovisionnements.smartshop.dto.User.Request;

import com.gestionapprovisionnements.smartshop.entity.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "name is required")
    private  String username ;
    @Email
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 8 , message = "password must at least 6 characters")
    private  String password ;
    @NotNull(message = "role is required")
    private UserRole role ;

}
