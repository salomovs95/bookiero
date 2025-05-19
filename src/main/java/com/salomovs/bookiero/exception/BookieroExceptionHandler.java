package com.salomovs.bookiero.exception;

import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookieroExceptionHandler {
  private Logger logger;

  public BookieroExceptionHandler() {
    logger = LoggerFactory.getLogger("Bookiero");
  }

  @ExceptionHandler(BookNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, Object>> handleException(BookNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(404).body(map);
  }

  @ExceptionHandler(UserNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, Object>> handleException(UserNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(404).body(map);
  }

  @ExceptionHandler(BorrowNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, Object>> handleException(BorrowNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(404).body(map);
  }

  @ExceptionHandler(BookBorrowingException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleException(BookBorrowingException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(400).body(map);
  }

  @ExceptionHandler(RuntimeException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, Object>> handleException(RuntimeException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(400).body(map);
  }


  @ExceptionHandler @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    Map<String, Object> map = new HashMap<>();
    map.put("error", ex.getMessage());

    return ResponseEntity.status(500).body(map);
  }
}
