package com.vaibhavTTN.BootCampProject.Ecommerce.ExceptionHandling;

import java.time.LocalDateTime;

public class ErrorDetail {
    private LocalDateTime timeStamp;
    private String message;
    private String details;

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
