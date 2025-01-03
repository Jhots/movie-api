package com.pagsestagio.movieapi.security.config;

import com.pagsestagio.movieapi.security.authentication.UserAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Autowired private UserAuthenticationFilter userAuthenticationFilter;

  public static final String[] ENDPOINTS_PUBLICOS = {"/v2/usuarios/login", "/v2/usuarios/criar"};

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()
        .requestMatchers(ENDPOINTS_PUBLICOS)
        .permitAll()
        .requestMatchers(HttpMethod.POST, "/v2/filmes")
        .hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.PUT, "/v2/filmes")
        .hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.DELETE, "/v2/filmes/**")
        .hasRole("ADMINISTRADOR")
        .requestMatchers("/v2/filmes/estatisticas")
        .hasRole("ADMINISTRADOR")
        .requestMatchers(HttpMethod.GET, "/v2/filmes/**")
        .authenticated()
        .anyRequest()
        .denyAll()
        .and()
        .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
