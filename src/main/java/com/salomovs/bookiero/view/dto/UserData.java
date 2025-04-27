package com.salomovs.bookiero.view.dto;

public record UserData(
  Integer id,
  String fullName,
  String username,
  String taxId,
  String email,
  String phone,
  String address,
  String role
) {}
