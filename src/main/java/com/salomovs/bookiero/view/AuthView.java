package com.salomovs.bookiero.view;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.utils.JwtUtil;
import com.salomovs.bookiero.view.dto.HttpResponse;
import com.salomovs.bookiero.view.dto.LoginResponse;
import com.salomovs.bookiero.view.dto.UserLoginDto;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(name="Authentication View", description="Handles user's registration and authentication")
@RestController @RequestMapping("/api/auth")
public class AuthView {
  private AuthController authController;
  private AuthenticationManager authManager;
  private JwtUtil jwtUtil;

  public AuthView(final AuthController authController,
                  final AuthenticationManager authManager,
                  final JwtUtil jwtUtil) {
    this.authController = authController;
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
  }

  @Operation(summary="User Registration Handler") @PostMapping("/signup")
  @ApiResponses({
    @ApiResponse(
      responseCode="201",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(implementation=HttpResponse.class)
        )
      }
    )
  })
  public ResponseEntity<HttpResponse> handleSignUp(@RequestBody UserSignUpDto body) {
    int newUserId = this.authController.saveUserInfo(body);
    return ResponseEntity.status(201).body(new HttpResponse(true, newUserId));
  }

  @Operation(summary="User Authentication Handler") @PostMapping("/login")
  public ResponseEntity<LoginResponse> handleLogin(@RequestBody UserLoginDto body) {
    Authentication authentication = this.authManager.authenticate(
      UsernamePasswordAuthenticationToken.unauthenticated(body.credential(), body.password())
    );

    User userDetails = (User) authentication.getPrincipal();
    String jwtToken = this.jwtUtil.issueToken(userDetails);
    LoginResponse res = new LoginResponse(userDetails, jwtToken);

    return ResponseEntity.status(200).body(res);
  }
}
