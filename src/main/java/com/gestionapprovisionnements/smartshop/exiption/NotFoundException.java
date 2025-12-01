package com.gestionapprovisionnements.smartshop.exiption;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
