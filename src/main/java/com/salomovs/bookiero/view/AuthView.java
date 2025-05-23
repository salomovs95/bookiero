package com.salomovs.bookiero.view;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.annotation.ApiOperation;
import com.salomovs.bookiero.config.security.JwtService;
import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.UserData;
import com.salomovs.bookiero.view.dto.UserLoginDto;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@Tag(name="Authentication View", description="Handles user's registration and authentication")
@RestController @RequestMapping("/api/auth")
public class AuthView {
  private AuthController authController;
  private AuthenticationManager authenticationManager;
  private JwtService jwtService;

  public AuthView(final AuthController authController,
                  final AuthenticationManager authenticationManager,
                  final JwtService jwtService
  ) {
    this.authController = authController;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @ApiOperation(summary="User Registration Handler", security="jwt")
  @PostMapping("/signup")
  public ResponseEntity<Map<String, Integer>> handleSignUp(@RequestBody UserSignUpDto body) {
    int newUserId = this.authController.saveUserInfo(body);
    Map<String, Integer> map = new HashMap<>();
    map.put("user_id", newUserId);

    return ResponseEntity.status(201).body(map);
  }

  @ApiOperation(summary="User Authentication Handler", security="")
  @PostMapping("/login")
  public ResponseEntity<Map<String, String>> handleLogin(@RequestBody UserLoginDto body) {
    Authentication authentication = this.authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken.unauthenticated(body.credential(), body.password())
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwtToken = this.jwtService.generateToken(userDetails);
    Map<String, String> map = new HashMap<>();
    map.put("access_token", jwtToken);
    
    return ResponseEntity.status(200).body(map);
  }

  @ApiOperation(summary="Retrieve User Info", security="jwt")
  @GetMapping("/users/info")
  public ResponseEntity<UserData> getUserInfo(@Schema(hidden=true) @RequestHeader("Authorization") String authorization) {
    String subject = this.jwtService.verify(authorization.replace("Bearer ", ""));
    User user = this.authController.loadUserByUsername(subject);

    return ResponseEntity.status(200).body(UserData.from(user));
  }
}
