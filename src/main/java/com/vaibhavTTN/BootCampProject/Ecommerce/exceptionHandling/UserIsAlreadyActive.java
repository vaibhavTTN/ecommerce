package com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserIsAlreadyActive extends RuntimeException {

  public UserIsAlreadyActive(String message) {
    super(message);
  }
}
