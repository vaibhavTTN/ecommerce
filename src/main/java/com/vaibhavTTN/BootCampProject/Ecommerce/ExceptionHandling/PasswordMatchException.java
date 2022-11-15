package com.vaibhavTTN.BootCampProject.Ecommerce.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class PasswordMatchException extends RuntimeException{
    public PasswordMatchException(String message){
        super(message);
    }
}
