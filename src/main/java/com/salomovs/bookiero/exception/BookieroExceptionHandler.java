package com.salomovs.bookiero.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.salomovs.bookiero.view.dto.HttpErrorResponse;

@RestControllerAdvice
public class BookieroExceptionHandler {
  @ExceptionHandler(BookNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpErrorResponse> handleException(BookNotFoundException ex) {
    return ResponseEntity.status(404).body(
      new HttpErrorResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(UserNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpErrorResponse> handleException(UserNotFoundException ex) {
    return ResponseEntity.status(404).body(
      new HttpErrorResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(BorrowNotFoundException.class) @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<HttpErrorResponse> handleException(BorrowNotFoundException ex) {
    return ResponseEntity.status(404).body(
      new HttpErrorResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(BookBorrowingException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<HttpErrorResponse> handleException(BookBorrowingException ex) {
    return ResponseEntity.status(400).body(
      new HttpErrorResponse(false, ex.getMessage())
    );
  }

  @ExceptionHandler(RuntimeException.class) @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<HttpErrorResponse> handleException(RuntimeException ex) {
    return ResponseEntity.status(400).body(
      new HttpErrorResponse(false, ex.getMessage())
    );
  }


  @ExceptionHandler @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<HttpErrorResponse> handleException(Exception ex) {
    return ResponseEntity.status(500).body(new HttpErrorResponse(false, ex.getMessage()));
  }
}
