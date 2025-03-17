package com.pagsestagio.movieapi.security.authentication;

import com.pagsestagio.movieapi.security.config.SecurityConfiguration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private JwtTokenService jwtTokenService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (checkIfEndpointIsNotPublic(request)) {
      String token = recoveryToken(request);
      if (token != null) {
        String subject = jwtTokenService.getSubjectFromToken(token);
        List<String> roles = jwtTokenService.getRolesFromToken(token);

        List<SimpleGrantedAuthority> authorities =
            roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        Authentication authentication =
            new UsernamePasswordAuthenticationToken(subject, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        throw new RuntimeException("O token está ausente.");
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoveryToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null) {
      return authorizationHeader.replace("Bearer ", "");
    }
    return null;
  }

  private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return !Arrays.asList(SecurityConfiguration.ENDPOINTS_PUBLICOS).contains(requestURI);
  }
}
