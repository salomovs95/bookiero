package com.salomovs.bookiero.config;

import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration @EnableWebSecurity @Profile("!test")
public class Security {
  @Value("${spring.api.cors.allowed-origins}")
  private String allowedOrigins;

  @Value("${spring.security.secret-random}")
  private String securityRandomSecret;

  @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
  private RSAPublicKey pubKey;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] whiteList = {
      "/v3/api-docs*/**",
      "/swagger-ui/**",
      "/api/auth/signup",
      "/api/auth/login"
    };

    http
      .cors(Customizer.withDefaults())
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

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
      .withPublicKey(pubKey)
      .build();

    OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
      new JwtTimestampValidator(Duration.ofMinutes(60)) //,
      //new JwtIssuerValidator(issuerUri)
    );

    jwtDecoder.setJwtValidator(withClockSkew);
    return jwtDecoder;
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthorityPrefix("");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  @Bean
  public AuthenticationManager authManager(PasswordEncoder passwordEncoder,
                                           UserDetailsService userDetailsService) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    
    authProvider.setPasswordEncoder(passwordEncoder);
    authProvider.setUserDetailsService(userDetailsService);

    return new ProviderManager(authProvider);
  }
}
