package com.salomovs.bookiero.mapper;

import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.UserData;

public class UserMapper {
  public static UserData userToData(User user) {
    return new UserData(
      user.getId(),
      user.getFullName(),
      user.getUsername(),
      user.getTaxId(),
      user.getEmail(),
      user.getPhone(),
      user.getAddress(),
      user.getRole());
  }
}
