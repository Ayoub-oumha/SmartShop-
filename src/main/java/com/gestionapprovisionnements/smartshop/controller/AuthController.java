package com.gestionapprovisionnements.smartshop.controller;

import com.gestionapprovisionnements.smartshop.dto.Auth.Request.LoginRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Request.UserRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import com.gestionapprovisionnements.smartshop.exiption.UnauthorizedAccessException;
import com.gestionapprovisionnements.smartshop.mapper.UserMapper;
import com.gestionapprovisionnements.smartshop.servec.AuthService;
import com.gestionapprovisionnements.smartshop.utils.UtilSession;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor

public class AuthController {
    private final AuthService  authService ;
    private final UserMapper userMapper ;
    @PostMapping("/createuser")
    public ResponseEntity<UserResponse>   createUser(@Valid @RequestBody UserRequest userRequest , HttpSession httpSession){
        if(!UtilSession.isAdmin(httpSession)){
            throw  new UnauthorizedAccessException("you must be admin to access this resourcce ") ;
        }
        UserResponse userResponse = authService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse)  ;
    }
    @PostMapping("/login")
    public ResponseEntity<UserResponse>  login(@Valid @RequestBody LoginRequest loginRequest , HttpSession httpSession){
        UserResponse userResponse = authService.login(loginRequest , httpSession) ;
        if(userResponse == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(userResponse) ;
    }
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Map<String , String>> logout(HttpSession httpSession) {
         authService.logout(httpSession);
         Map<String , String> body = Map.of("message" , "Déconnexion réussie") ;
         return ResponseEntity.ok(body) ;
    }

}
