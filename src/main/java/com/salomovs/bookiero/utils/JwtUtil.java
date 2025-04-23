package com.salomovs.bookiero.utils;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
  @Value("${spring.security.secret-random}")
  private String securityRandomSecret;

  public String issueToken(UserDetails userDetails) {
    SecureRandom secureRandom = new SecureRandom(securityRandomSecret.getBytes());
    Map<String, String> claims = new HashMap<>();

    claims.put("authorities", userDetails.getAuthorities().toString());

    JwtBuilder jwtBuilder = Jwts.builder()
      .random(secureRandom)
      .subject(userDetails.getUsername())
      .claims(claims)
      .expiration(Date.from(Instant.now().plusSeconds(60*60)));

    return jwtBuilder.compact();
  }
}
