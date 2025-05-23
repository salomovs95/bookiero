package com.salomovs.bookiero.controller;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salomovs.bookiero.enums.Roles;
import com.salomovs.bookiero.exception.UserNotFoundException;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.model.repository.UserRepository;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@Service
public class AuthController implements UserDetailsService {
  private UserRepository userRepo;
  private PasswordEncoder pwEncoder;

  public AuthController(final UserRepository userRepo,
                        final PasswordEncoder pwEncoder) {
    this.userRepo = userRepo;
    this.pwEncoder = pwEncoder;
  }

  public Integer saveUserInfo(UserSignUpDto dto) {
    User newUser = this.userRepo.save(new User(
      null,
      dto.fullName(),
      dto.username(),
      dto.taxId(),
      pwEncoder.encode(dto.password()),
      dto.email(),
      dto.phone(),
      dto.address(),
      Roles.valueOf(dto.role().orElse("USER_COMMON")).toString(),
      LocalDate.now()
    ));

    return newUser.getId();
  }

  public User getUserInfo(Integer userId) {
    User user = this.userRepo
      .findById(userId)
      .orElseThrow(()->new UserNotFoundException(userId));

    return user;
  }

  public User loadUserByUsername(String credential) {
    User user = this.userRepo
      .findByUsernameOrEmail(credential)
      .orElseThrow(()->new UserNotFoundException(credential));

    return user;
  }

  public Long countUsers() {
    return this.userRepo.count();
  }
}
