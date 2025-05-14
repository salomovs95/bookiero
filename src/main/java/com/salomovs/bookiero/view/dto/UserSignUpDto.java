package com.salomovs.bookiero.view.dto;

import java.util.Optional;

public record UserSignUpDto(
  String fullName,
  String username,
  String taxId,
  String phone,
  String email,
  String password,
  String address,
  Optional<String> role
) {}
