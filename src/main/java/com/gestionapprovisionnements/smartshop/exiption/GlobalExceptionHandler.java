package com.gestionapprovisionnements.smartshop.exiption;

import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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
    // UnauthorizedAccessException just for admin or Client
    @ExceptionHandler(UnauthorizedAccessException.class)
    public  ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException unauthorizedAccessException){
        HashMap<String , Object> body = new HashMap<>() ;
        body.put("message" , unauthorizedAccessException.getMessage()) ;
        body.put("status" , HttpStatus.UNAUTHORIZED.value()) ;
        body.put("error" , "UnauthorizedAccess") ;
        return new ResponseEntity<>(body , HttpStatus.UNAUTHORIZED) ;
    }
    //vlaidation

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        body.put("message", "Validation failed");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Error");
        body.put("fields", fieldErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    //alreadyexists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAlreadyExists(ResourceAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "fail");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
    }
    //path not found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Endpoint not found: " + ex.getRequestURL());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    // Catch all unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", "Something went wrong. Please try again later.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Optional: handle specific exceptions like NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNullPointer(NullPointerException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("status", "error");
        error.put("message", "A required service or resource is missing.");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

