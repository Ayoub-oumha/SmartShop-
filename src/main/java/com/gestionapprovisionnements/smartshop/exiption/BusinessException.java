package com.gestionapprovisionnements.smartshop.exiption;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) {
    super(message);
  }
}
