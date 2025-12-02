package com.gestionapprovisionnements.smartshop.utils;

import com.gestionapprovisionnements.smartshop.entity.enums.UserRole;
import jakarta.servlet.http.HttpSession;

public class UtilSession {
    public  static boolean isAdmin(HttpSession httpSession){
        if(httpSession == null) return false ;
        // casting bach trj3o type Enum
        UserRole userRole = (UserRole) httpSession.getAttribute("userRole") ;
        return userRole == UserRole.ADMIN ;
    }
    public static boolean isClient(HttpSession httpSession){
        if(httpSession ==  null) return  false ;
        UserRole userRole = (UserRole) httpSession.getAttribute("userRole") ;
        return userRole == UserRole.CLIENT ;
    }
    public  static Long getUserIdInSession(HttpSession httpSession){
        if (httpSession == null) return  null ;
        Long id = (Long) httpSession.getAttribute("userId") ;
        if (id == null) return null ;
        return id ;
    }
}
