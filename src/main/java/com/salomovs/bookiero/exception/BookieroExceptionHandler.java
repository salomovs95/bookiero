package com.salomovs.bookiero.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salomovs.bookiero.view.dto.HttpResponse;

@RestControllerAdvice
public class BookieroExceptionHandler {
  private Logger logger;

  public BookieroExceptionHandler() {
    logger = LoggerFactory.getLogger("Bookiero");
  }

  @ExceptionHandler(BookNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpResponse> handleException(BookNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(404).body(
      new HttpResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(UserNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpResponse> handleException(UserNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(404).body(
      new HttpResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(BorrowNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpResponse> handleException(BorrowNotFoundException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(404).body(
      new HttpResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(BookBorrowingException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<HttpResponse> handleException(BookBorrowingException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(400).body(
      new HttpResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(RuntimeException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<HttpResponse> handleException(RuntimeException ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(400).body(
      new HttpResponse(false, ex.getMessage())
    );
  }


  @ExceptionHandler @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<HttpResponse> handleException(Exception ex) {
    logger.error(ex.getMessage());
    ex.printStackTrace();

    return ResponseEntity.status(500).body(new HttpResponse(false, ex.getMessage()));
  }
}
