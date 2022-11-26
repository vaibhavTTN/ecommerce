package com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.AccountLockedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail(
        LocalDateTime.now(),
        ex.getMessage(),
        request.getDescription(false)
    );
    return new ResponseEntity<Object>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public final ResponseEntity<ErrorDetail> handleEmployeeNotFoundException(Exception ex,
      WebRequest request) {
    ErrorDetail errorDetail = new ErrorDetail(
        LocalDateTime.now(),
        ex.getMessage(),
        request.getDescription(false)
    );
    return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.NOT_FOUND);
  }

   @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> validationErrors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    ErrorDetail errorDetail = new ErrorDetail(
        LocalDateTime.now(),
        ex.getMessage(),
        request.getDescription(false)
    );
    return new ResponseEntity<Object>(errorDetail, HttpStatus.BAD_REQUEST);
  }

}