package com.salomovs.bookiero.view.dto;

public record UserSignUpDto(
  String fullName,
  String username,
  String taxId,
  String phone,
  String email,
  String password,
  String address
) {}
