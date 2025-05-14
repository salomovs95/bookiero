package com.salomovs.bookiero.config.security;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtService {
  @Value("${spring.security.jwt.secret}")
  private String jwtSecret;
  @Value("${spring.security.jwt.issuer}")
  private String jwtIssuer;

  private Long tokenExpirationInSeconds = 60 * 60l;

  public String generateToken(UserDetails user) {
    var claims = user.getAuthorities()
                     .stream()
                     .map(authority->authority.getAuthority())
                     .collect(Collectors.toList());

    return JWT.create()
              .withIssuer(jwtIssuer)
              .withExpiresAt(Instant.now().plusSeconds(tokenExpirationInSeconds))
              .withSubject(user.getUsername())
              .withClaim("auhtorities", claims)
              .sign(getAlgorithm());
  }

  public String verify(String token) {
    return JWT.require(getAlgorithm())
              .withIssuer(jwtIssuer)
              .build()
              .verify(token)
              .getSubject();
  }

  private Algorithm getAlgorithm() {
    return Algorithm.HMAC256(jwtSecret.getBytes());
  }
}
