package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Auth.Request.LoginRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import com.gestionapprovisionnements.smartshop.mapper.UserMapper;
import com.gestionapprovisionnements.smartshop.servec.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthController {
    AuthService authService ;
    UserMapper userMapper ;
    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest loginRequest , HttpSession httpSession){
        return authService.login(loginRequest , httpSession) ;
    }
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession httpSession) {
         authService.logout(httpSession);
    }

}
