package com.salomovs.bookiero.config.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoding {
  @Value("${spring.security.secret-random}")
  private String randomSecret;

  @Bean
  public PasswordEncoder passwordEncoder() {
    SecureRandom secureRandom = new SecureRandom(randomSecret.getBytes());
    return new BCryptPasswordEncoder(12, secureRandom);
  }
}
