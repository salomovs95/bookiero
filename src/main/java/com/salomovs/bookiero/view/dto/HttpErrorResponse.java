package com.salomovs.bookiero.view.dto;

public record HttpErrorResponse(
  Boolean ok,
  String error
) {}
