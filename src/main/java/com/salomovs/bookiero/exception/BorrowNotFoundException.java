package com.salomovs.bookiero.exception;

public class BorrowNotFoundException extends RuntimeException {
  public BorrowNotFoundException(String message) {
    super(message);
  }

  public BorrowNotFoundException(Integer borrowId) {
    super("No Borrow Was Found With ID: " + borrowId);
  }
}
