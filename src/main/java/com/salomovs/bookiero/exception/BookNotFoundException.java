package com.salomovs.bookiero.exception;

public class BookNotFoundException extends RuntimeException {
  public BookNotFoundException(String message) {
    super(message);
  }

  public BookNotFoundException(Integer bookId) {
    super("No Book Was Found With ID: " + bookId);
  }
}
