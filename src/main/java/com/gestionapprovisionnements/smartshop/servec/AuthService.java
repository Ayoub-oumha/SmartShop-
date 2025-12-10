package com.gestionapprovisionnements.smartshop.servec;

import com.gestionapprovisionnements.smartshop.dto.Auth.Request.LoginRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Request.UserRequest;
import com.gestionapprovisionnements.smartshop.dto.User.Response.UserResponse;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
     UserResponse createUser(UserRequest userRequest) ;
     UserResponse login(LoginRequest loginRequest , HttpSession httpSession);
     void  logout(HttpSession httpSession)  ;

}
