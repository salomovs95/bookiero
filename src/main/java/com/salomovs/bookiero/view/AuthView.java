package com.salomovs.bookiero.view;

import java.time.Instant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.HttpResponse;
import com.salomovs.bookiero.view.dto.UserLoginDto;
import com.salomovs.bookiero.view.dto.UserSignUpDto;

@Tag(name="Authentication View", description="Handles user's registration and authentication")
@RestController @RequestMapping("/api/auth")
public class AuthView {
  private AuthController authController;
  private AuthenticationManager authManager;
  private JwtEncoder jwtEncoder;

  public AuthView(final AuthController authController,
                  final AuthenticationManager authManager,
                  final JwtEncoder encoder
  ) {
    this.authController = authController;
    this.authManager = authManager;
    this.jwtEncoder = encoder;
  }

  @Operation(
    summary="User Registration Handler",
    security={@SecurityRequirement(name="")}
  )
  @ApiResponses({
    @ApiResponse(
      responseCode="201",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(implementation=HttpResponse.class)
        )
      }
    ),
    @ApiResponse(
      responseCode="400",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(
            implementation=HttpResponse.class,
            example="{\"ok\": false, \"payload\": \"bad request\" }"
          )
        )
      }
    )
  })
  @PostMapping("/signup")
  public ResponseEntity<HttpResponse> handleSignUp(@RequestBody UserSignUpDto body) {
    int newUserId = this.authController.saveUserInfo(body);
    return ResponseEntity.status(201).body(new HttpResponse(true, newUserId));
  }

  @Operation(
    summary="User Authentication Handler",
    security={@SecurityRequirement(name="")}
  )
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(
            implementation=HttpResponse.class,
            example="{ \"ok\": true, \"payload\": \"tkVmDYfqVlSi1oyLf9QF-0AMd4v\" }"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="400",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(
            implementation=HttpResponse.class,
            example="{ \"ok\": false, \"payload\": \"invalid credentials\" }"
          )
        )
      }
    ),
    @ApiResponse(
      responseCode="404",
      content={
        @Content(
          mediaType="application/json",
          schema=@Schema(
            implementation = HttpResponse.class,
            example="{ \"ok\": false, \"payload\": \"user not found\" }"
          )
        )
      }
    )
  })
  @PostMapping("/login")
  public ResponseEntity<HttpResponse> handleLogin(@RequestBody UserLoginDto body) {
    Authentication authentication = this.authManager.authenticate(
      UsernamePasswordAuthenticationToken.unauthenticated(body.credential(), body.password())
    );

    User userDetails = (User) authentication.getPrincipal();
    String jwtToken = this.jwtEncoder.encode(
      JwtEncoderParameters.from(
        JwtClaimsSet
          .builder()
          .claim("username", userDetails.getUsername())
          .claim("role", userDetails.getRole())
          .issuedAt(Instant.now())
          .expiresAt(Instant.now().plusSeconds(60*60))
          .subject(userDetails.getEmail())
          .build()
      )
    ).getTokenValue();

    return ResponseEntity.status(200).body(new HttpResponse(true, jwtToken));
  }
}
