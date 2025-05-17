package com.salomovs.bookiero.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.salomovs.bookiero.enums.Roles;

@Configuration @EnableWebSecurity @Profile("!test")
public class FilterChain {
  @Value("${spring.api.cors.allowed-origins}")
  private String allowedOrigins;

  @Autowired
  private JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] publicRoutes = {
      "/v3/api-docs*/**",
      "/swagger-ui/**",
      "/api/auth/login",
      "/api/auth/signup"
    };

    String[] adminPostRoutes = {
      "/api/books/",
      "/api/books/borrows/**",
      "/api/books/authors"
    };

    String[] adminPatchRoutes = { "/api/books/borrows/**" };

    http
      .cors(Customizer.withDefaults())
      .csrf(c -> c.disable())
      .sessionManagement(session -> session
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(publicRoutes)
           .permitAll()
        .requestMatchers(HttpMethod.POST, adminPostRoutes)
           .hasRole(Roles.USER_ADMIN.toString())
        .requestMatchers(HttpMethod.PATCH, adminPatchRoutes)
           .hasRole(Roles.USER_ADMIN.toString())
        .anyRequest()
           .authenticated()
      )
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    
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
}
