package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaTokenOuMensagem;
import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaUsuarioOuMensagem;
import com.pagsestagio.movieapi.model.*;
import com.pagsestagio.movieapi.repository.UsuarioRepository;
import com.pagsestagio.movieapi.security.authentication.JwtTokenService;
import com.pagsestagio.movieapi.security.config.SecurityConfiguration;
import com.pagsestagio.movieapi.security.userDetails.UserDetailsImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private JwtTokenService jwtTokenService;

  @Autowired private UsuarioRepository usuarioRepository;

  @Autowired private SecurityConfiguration securityConfiguration;

  public UsuarioRespostaRetornaTokenOuMensagem autenticarUsuario(LoginUsuarioDTO loginUsuarioDTO) {
    UsuarioRespostaRetornaTokenOuMensagem retornoAutenticacaoUsuario = null;

    Optional<Usuario> usuarioExistente =
        usuarioRepository.findByNomeUsuario(loginUsuarioDTO.nomeUsuario());

    if (loginUsuarioDTO.nomeUsuario() == null || loginUsuarioDTO.senha() == null) {
      retornoAutenticacaoUsuario =
          new UsuarioRespostaRetornaTokenOuMensagem(
              null,
              "Não foi possível fazer login com estas credenciais. Verifique os dados informados.");
    } else if (usuarioExistente.isEmpty()) {
      retornoAutenticacaoUsuario =
          new UsuarioRespostaRetornaTokenOuMensagem(
              null, "Usuário não cadastrado. Tente criar um usuário.");
    } else {
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(
              loginUsuarioDTO.nomeUsuario(), loginUsuarioDTO.senha());
      Authentication authentication =
          authenticationManager.authenticate(usernamePasswordAuthenticationToken);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      String token = jwtTokenService.generateToken(userDetails);

      retornoAutenticacaoUsuario = new UsuarioRespostaRetornaTokenOuMensagem(token, null);
    }

    return retornoAutenticacaoUsuario;
  }

  public UsuarioRespostaRetornaUsuarioOuMensagem criarUsuario(CriacaoUsuarioDTO criacaoUsuarioDTO) {
    UsuarioRespostaRetornaUsuarioOuMensagem retornoCriacaoDeUsuario = null;

    Optional<Usuario> usuarioExistente =
        usuarioRepository.findByNomeUsuario(criacaoUsuarioDTO.nomeUsuario());

    if (criacaoUsuarioDTO.nomeUsuario() == null
        || criacaoUsuarioDTO.senha() == null
        || criacaoUsuarioDTO.funcao() == null) {
      retornoCriacaoDeUsuario =
          new UsuarioRespostaRetornaUsuarioOuMensagem(
              "Não foi possível criar este usuário. Verifique os dados informados.");
    } else if (usuarioExistente.isPresent()) {
      retornoCriacaoDeUsuario =
          new UsuarioRespostaRetornaUsuarioOuMensagem("Usuário já cadastrado. Tente fazer login.");
    } else {
      Funcao funcao = new Funcao(null, criacaoUsuarioDTO.funcao());
      Usuario novoUsuario =
          new Usuario(
              null,
              criacaoUsuarioDTO.nomeUsuario(),
              securityConfiguration.passwordEncoder().encode(criacaoUsuarioDTO.senha()),
              List.of(funcao));

      usuarioRepository.save(novoUsuario);

      retornoCriacaoDeUsuario =
          new UsuarioRespostaRetornaUsuarioOuMensagem(
              novoUsuario.getId(),
              novoUsuario.getNomeUsuario(),
              novoUsuario.getSenha(),
              novoUsuario.getFuncoes().getFirst().getNome().name());
    }

    return retornoCriacaoDeUsuario;
  }
}
