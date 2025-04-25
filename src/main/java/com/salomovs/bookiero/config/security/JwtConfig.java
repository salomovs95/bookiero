package com.salomovs.bookiero.config.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration @Profile("!test")
public class JwtConfig {
  @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
  private RSAPublicKey pubKey;

  @Value("${spring.security.oauth2.resourceserver.jwt.private-key-location}")
  private RSAPrivateKey privKey;

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = new RSAKey
      .Builder(pubKey)
			.privateKey(privKey)
			.keyID(UUID.randomUUID().toString())
			.build();

    JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    NimbusJwtDecoder decoder = NimbusJwtDecoder
      .withPublicKey(pubKey)
      .build();

    OAuth2TokenValidator<Jwt> withClockSkew = new DelegatingOAuth2TokenValidator<>(
      new JwtTimestampValidator(Duration.ofMinutes(60)) //,
      //new JwtIssuerValidator(issuerUri)
    );

    decoder.setJwtValidator(withClockSkew);
    return decoder;
  }

  @Bean
  public JwtEncoder jwtEncoder(JWKSource<SecurityContext> source) {
    return new NimbusJwtEncoder(source);
  }
}
