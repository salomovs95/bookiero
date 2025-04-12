package com.salomovs.bookiero.controller;

import org.springframework.stereotype.Service;

import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.model.repository.UserRepository;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@Service
public class AuthController {
  private UserRepository userRepo;

  public AuthController(final UserRepository userRepo) {
    this.userRepo = userRepo;
  }

  public Integer register(UserSignUpDto dto) {
    User newUser = this.userRepo.save(new User(
      null,
      dto.fullName(),
      dto.taxId(),
      dto.password(),
      dto.email(),
      dto.phone(),
      dto.address()
    ));

    return newUser.getId();
  }

  public User getUserInfo(Integer userId) {
    User user = userRepo.findById(userId)
      .orElseThrow(()->new RuntimeException("User Not Found [" + userId + "]"));
    return user;
  }
}
