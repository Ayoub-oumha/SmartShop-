package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Auth.Request.LoginRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Request.UserRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import com.gestionapprovisionnements.smartshop.entity.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    public UserResponse createUser(UserRequest userRequest) ;
    public UserResponse login(LoginRequest loginRequest , HttpSession httpSession);
    public void  logout(HttpSession httpSession)  ;

}
