package com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling;

public class BadCredentialsException extends RuntimeException{
  public BadCredentialsException(String message) {
    super(message);
  }
}
