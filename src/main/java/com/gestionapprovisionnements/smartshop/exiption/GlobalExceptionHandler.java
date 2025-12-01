package com.gestionapprovisionnements.smartshop.exiption;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // user  not found
   @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String , Object>> handelNotFoundException(NotFoundException notFoundException){
       Map<String , Object> body= new HashMap<>() ;
       body.put("message", notFoundException.getMessage()) ;
       body.put("status", HttpStatus.NOT_FOUND.value()) ;
       body.put("error" , "Not Found");
       return  new ResponseEntity<>(body , HttpStatus.NOT_FOUND);
   }
   //password not correct
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String, Object>> handelIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
       HashMap<String , Object> body = new HashMap<>() ;
       body.put("message", incorrectPasswordException.getMessage()) ;
       body.put("status", HttpStatus.UNAUTHORIZED.value());
       body.put("error" , "incorrect password") ;
       return  new  ResponseEntity<>(body , HttpStatus.UNAUTHORIZED) ;

    }
}
