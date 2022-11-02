package hu.marko.szakdolgozat.spring.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import hu.marko.szakdolgozat.spring.controller.model.Message;
import hu.marko.szakdolgozat.spring.exception.BadRequestException;
import hu.marko.szakdolgozat.spring.exception.NotFoundException;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = BadRequestException.class)
  protected ResponseEntity<Object> handleBadRequestExceptions(BadRequestException ex, WebRequest request) {
    return handleExceptionInternal(ex, new Message(ex.getMessage()),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = NotFoundException.class)
  protected ResponseEntity<Object> handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
    return handleExceptionInternal(ex, new Message(ex.getMessage()),
        new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}
