package com.salomovs.bookiero.exception;

public class AuthorNotFoundException extends RuntimeException {
  public AuthorNotFoundException(String message) {
    super(message);
  }

  public AuthorNotFoundException(Integer authorId) {
    super("No Author Was Found With ID: " + authorId);
  }
}
