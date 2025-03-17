package com.pagsestagio.movieapi.security.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pagsestagio.movieapi.security.userDetails.UserDetailsImpl;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
  private static final String SECRET_KEY = "${api.security.secret.key}";
  private static final String ISSUER = "${api.security.issuer}";

  public String generateToken(UserDetailsImpl user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

      List<String> authorities =
          user.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList());

      return JWT.create()
          .withIssuer(ISSUER)
          .withIssuedAt(creationDate())
          .withExpiresAt(expirationDate())
          .withSubject(user.getUsername())
          .withClaim("roles", authorities)
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new JWTCreationException("Erro ao gerar token.", exception);
    }
  }

  public String getSubjectFromToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
    } catch (JWTVerificationException exception) {
      throw new JWTVerificationException("Token inválido ou expirado.");
    }
  }

  public List<String> getRolesFromToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
      DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);
      return jwt.getClaim("roles").asList(String.class);
    } catch (JWTVerificationException exception) {
      throw new JWTVerificationException("Token inválido ou expirado.");
    }
  }

  private Instant creationDate() {
    return ZonedDateTime.now(ZoneId.of("America/Recife")).toInstant();
  }

  private Instant expirationDate() {
    return ZonedDateTime.now(ZoneId.of("America/Recife")).plusHours(6).toInstant();
  }
}
