package com.salomovs.bookiero.config.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.model.entity.User;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
  private AuthController authController;

  @Autowired
  private JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    Optional<String> token = extractToken(request);
    if (token.isPresent()) {
      String jwt = token.get();
      String username = jwtService.verify(jwt);
      User user = authController.loadUserByUsername(username);
      var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    var authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null) return Optional.empty();
    return Optional.of(authorizationHeader.replace("Bearer ", ""));
  }
}
