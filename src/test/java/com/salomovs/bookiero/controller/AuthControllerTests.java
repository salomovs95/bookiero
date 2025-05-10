package com.salomovs.bookiero.controller;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.salomovs.bookiero.model.repository.UserRepository;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@Tag("AUTHENTICATION")
@SpringBootTest @TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerTests {
  @Autowired
  private UserRepository uRepo;

  @Autowired
  private PasswordEncoder pwEncoder;

  private AuthController aController;

  @BeforeAll
  public void setup() {
    aController = new AuthController(uRepo, pwEncoder);
  }

  @Test
  public void ShouldCreateUserSuccess() {
    UserSignUpDto newUser = new UserSignUpDto("user 001", "user001", "user001-tax-id", "user001@password", "ueer001_email@test.io", "user001-phone", "user001 st., address");
    assertDoesNotThrow(()->aController.saveUserInfo(newUser));
  }

  @Test
  public void ShouldCreateUserFail() {
    UserSignUpDto newUser = new UserSignUpDto(null, null, null, null, null, null, null);
    assertThrows(Exception.class, ()->aController.saveUserInfo(newUser));
  }
}
