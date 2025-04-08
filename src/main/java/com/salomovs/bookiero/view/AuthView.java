package com.salomovs.bookiero.view;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@RestController @RequestMapping("/api/auth")
public class AuthView {
  private AuthController authController;

  public AuthView(final AuthController authController) {
    this.authController = authController;
  }

  @PostMapping("/signup")
  public ResponseEntity<Integer> handleSignUp(@RequestBody UserSignUpDto body) {
    int newUserId = this.authController.register(body);
    return ResponseEntity.status(201).body(newUserId);
  }
}
