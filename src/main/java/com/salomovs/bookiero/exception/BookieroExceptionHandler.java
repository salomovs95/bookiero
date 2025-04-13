package com.salomovs.bookiero.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookieroExceptionHandler {
  @ExceptionHandler
  public ResponseEntity<?> handleException(RuntimeException ex) {
    int statusCode = 400;

    if (ex instanceof UserNotFoundException || ex instanceof BookNotFoundException) {
      statusCode = 404;
    }

    return ResponseEntity.status(statusCode).body(
      String.format("{\"ok\": false, \"error\": \"%s\"}", ex.getMessage())
    );
  }

  public ResponseEntity<?> handleException(Exception ex) {
    return ResponseEntity.status(500).body(
      String.format("{\"ok\": false, \"error\": \"%s\"}", ex.getMessage())
    );
  }
}
