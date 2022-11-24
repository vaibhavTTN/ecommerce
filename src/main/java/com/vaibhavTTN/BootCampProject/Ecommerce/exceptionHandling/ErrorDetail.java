package com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling;

import java.time.LocalDateTime;

public class ErrorDetail {

  private final LocalDateTime timeStamp;
  private final String message;
  private final String details;

  public ErrorDetail(LocalDateTime timeStamp, String message, String details) {
    super();
    this.timeStamp = timeStamp;
    this.message = message;
    this.details = details;
  }

  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }
}
