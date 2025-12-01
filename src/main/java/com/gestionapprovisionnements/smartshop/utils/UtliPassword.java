package com.gestionapprovisionnements.smartshop.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UtliPassword {
    public static String hashPassword (String password){
        return BCrypt.withDefaults().hashToString(12, password.toCharArray()) ;
    }
    public static Boolean verifyPassword(String plainPassword , String hashPassword){
        return  BCrypt.verifyer().verify(plainPassword.toCharArray() , hashPassword).verified ;
    }
}
