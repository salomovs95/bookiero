package com.salomovs.bookiero.view;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.view.dto.HttpResponse;
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

  public AuthView(final AuthController authController) {
    this.authController = authController;
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
    int newUserId = this.authController.register(body);
    return ResponseEntity.status(201).body(new HttpResponse(true, newUserId));
  }
}
