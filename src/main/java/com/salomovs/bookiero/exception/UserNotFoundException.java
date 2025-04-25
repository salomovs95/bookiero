package com.salomovs.bookiero.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(Integer userId) {
    super("No User Was Found With ID: " + userId);
  }
}
