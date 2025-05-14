package com.salomovs.bookiero.view.dto;

import java.time.LocalDate;

import com.salomovs.bookiero.model.entity.User;

public record UserData(
  Integer id,
  String fullName,
  String username,
  String taxId,
  String email,
  String phone,
  String address,
  String role,
  LocalDate joinedAt
) {
  public static UserData from(User user) {
    return new UserData(user.getId(), user.getFullName(), user.getUsername(), user.getTaxId(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole(), user.getJoinedAt());
  }
}
