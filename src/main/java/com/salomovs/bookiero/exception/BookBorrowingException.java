package com.salomovs.bookiero.exception;

public class BookBorrowingException extends RuntimeException {
  public BookBorrowingException(String message) {
    super(message);
  }

  public BookBorrowingException() {
    super("Could Not Borrow This Time");
  }
}
