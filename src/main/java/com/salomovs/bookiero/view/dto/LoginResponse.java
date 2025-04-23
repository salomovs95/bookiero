package com.salomovs.bookiero.view.dto;

import com.salomovs.bookiero.model.entity.User;

public record LoginResponse(
  User user,
  String jwtToken
) {}
