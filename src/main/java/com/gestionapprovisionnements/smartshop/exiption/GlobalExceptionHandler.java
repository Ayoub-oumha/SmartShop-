package com.gestionapprovisionnements.smartshop.exiption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // user  not found
   @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String , Object>> handelNotFoundException(NotFoundException notFoundException, HttpServletRequest request){
       Map<String , Object> body= new HashMap<>() ;
       body.put("timestamp", Instant.now().toString());
       body.put("status", HttpStatus.NOT_FOUND.value());
       body.put("error" , "Not Found");
       body.put("message", notFoundException.getMessage());
       body.put("path", request.getRequestURI());
       return  new ResponseEntity<>(body , HttpStatus.NOT_FOUND);
   }
   //password not correct
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String, Object>> handelIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException, HttpServletRequest request){
       Map<String , Object> body = new HashMap<>() ;
       body.put("timestamp", Instant.now().toString());
       body.put("message", incorrectPasswordException.getMessage()) ;
       body.put("status", HttpStatus.UNAUTHORIZED.value());
       body.put("error" , "Incorrect Password") ;
       body.put("path", request.getRequestURI());
       return  new  ResponseEntity<>(body , HttpStatus.UNAUTHORIZED) ;

    }
    // UnauthorizedAccessException just for admin or Client
    @ExceptionHandler(UnauthorizedAccessException.class)
    public  ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(UnauthorizedAccessException unauthorizedAccessException, HttpServletRequest request){
        Map<String , Object> body = new HashMap<>() ;
        body.put("timestamp", Instant.now().toString());
        body.put("message" , unauthorizedAccessException.getMessage()) ;
        body.put("status" , HttpStatus.FORBIDDEN.value()) ;
        body.put("error" , "Forbidden") ;
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body , HttpStatus.FORBIDDEN) ;
    }
    // Business exception -> 422
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        body.put("error", "Unprocessable Entity");
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    //vlaidation

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        body.put("timestamp", Instant.now().toString());
        body.put("message", "Validation failed");
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Error");
        body.put("fields", fieldErrors);
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    //alreadyexists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleAlreadyExists(ResourceAlreadyExistsException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT); // 409 Conflict
    }
    //path not found
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFound(NoHandlerFoundException ex, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("message", "Endpoint not found: " + ex.getRequestURL());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    // Catch all unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllExceptions(Exception ex, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("message", "Something went wrong. Please try again later.");
        error.put("path", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Optional: handle specific exceptions like NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, Object>> handleNullPointer(NullPointerException ex, HttpServletRequest request) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now().toString());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("message", "A required service or resource is missing.");
        error.put("path", request.getRequestURI());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
