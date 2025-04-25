package com.salomovs.bookiero.config.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration @EnableWebSecurity @Profile("!test")
public class FilterChain {
  @Value("${spring.api.cors.allowed-origins}")
  private String allowedOrigins;

  @Value("${spring.security.secret-random}")
  private String securityRandomSecret;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] whiteList = {
      "/v3/api-docs*/**",
      "/swagger-ui/**",
      "/api/auth/signup",
      "/api/auth/login"
    };

    http
      //.cors(Customizer.withDefaults())
      .csrf(c -> c.disable())
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(whiteList).permitAll()
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(oauth -> oauth
        .jwt(Customizer.withDefaults())
      );
    
    return http.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    
    config.setAllowCredentials(true);
    config.addAllowedOrigin(allowedOrigins);
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    
    return new CorsFilter(source);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    SecureRandom secureRandom = new SecureRandom(securityRandomSecret.getBytes());
    return new BCryptPasswordEncoder(16, secureRandom);
  }
}
