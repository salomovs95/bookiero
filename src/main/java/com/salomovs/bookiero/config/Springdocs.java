package com.salomovs.bookiero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Springdocs {
  @Bean
  public OpenAPI openAPI() {
    OpenAPI api = new OpenAPI();
    Info info = new Info();
    SecurityScheme security = new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");

    return api.info(
      info.version("0.1.1")
          .title("Bookiero API")
          .description("An API related to books borrowing.")
    ).components(
      new Components().addSecuritySchemes("jwt", security)
    );
  }
}
