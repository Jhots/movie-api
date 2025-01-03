package com.pagsestagio.movieapi.service;

import static com.pagsestagio.movieapi.model.NomeFuncao.USUARIO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaTokenOuMensagem;
import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaUsuarioOuMensagem;
import com.pagsestagio.movieapi.model.CriacaoUsuarioDTO;
import com.pagsestagio.movieapi.model.LoginUsuarioDTO;
import com.pagsestagio.movieapi.model.Usuario;
import com.pagsestagio.movieapi.repository.UsuarioRepository;
import com.pagsestagio.movieapi.security.authentication.JwtTokenService;
import com.pagsestagio.movieapi.security.config.SecurityConfiguration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTests {
  private AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
  private JwtTokenService jwtTokenService = Mockito.mock(JwtTokenService.class);
  private UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
  private SecurityConfiguration securityConfiguration = Mockito.mock(SecurityConfiguration.class);

  private UsuarioService usuarioService =
      new UsuarioService(
          authenticationManager, jwtTokenService, usuarioRepository, securityConfiguration);

  @BeforeEach
  public void setUp() {
    usuarioService =
        new UsuarioService(
            authenticationManager, jwtTokenService, usuarioRepository, securityConfiguration);
  }

  @Test
  public void deveAutenticarUsuarioComSucessoERetornarToken() {
    Usuario usuario = new Usuario(null, "usuarioTeste", "senhaCriptografada", List.of());
    Mockito.when(usuarioRepository.findByNomeUsuario("usuarioTeste"))
        .thenReturn(Optional.of(usuario));
    Mockito.when(authenticationManager.authenticate(Mockito.any()))
        .thenReturn(Mockito.mock(Authentication.class));
    Mockito.when(jwtTokenService.generateToken(Mockito.any())).thenReturn("tokenGerado");

    LoginUsuarioDTO loginDTO = new LoginUsuarioDTO("usuarioTeste", "senha");

    UsuarioRespostaRetornaTokenOuMensagem resultado = usuarioService.autenticarUsuario(loginDTO);

    assertNotNull(resultado.token());
    assertNull(resultado.mensagem());
    assertEquals("tokenGerado", resultado.token());
  }

  @Test
  public void naoDeveAutenticarUsuarioRetornandoMensagemDeUsuarioNaoCadastrado() {
    Mockito.when(usuarioRepository.findByNomeUsuario("usuarioInexistente"))
        .thenReturn(Optional.empty());

    LoginUsuarioDTO loginDTO = new LoginUsuarioDTO("usuarioInexistente", "senha");

    UsuarioRespostaRetornaTokenOuMensagem resultado = usuarioService.autenticarUsuario(loginDTO);

    assertNull(resultado.token());
    assertEquals("Usuário não cadastrado. Tente criar um usuário.", resultado.mensagem());
  }

  @Test
  public void naoDeveAutenticarUsuarioRetornandoMensagemDeCredenciaisInvalidas() {
    LoginUsuarioDTO loginDTO = new LoginUsuarioDTO("usuarioTeste", null);

    UsuarioRespostaRetornaTokenOuMensagem resultado = usuarioService.autenticarUsuario(loginDTO);

    assertNull(resultado.token());
    assertEquals(
        "Não foi possível fazer login com estas credenciais. Verifique os dados informados.",
        resultado.mensagem());
  }

  @Test
  public void deveCriarUsuarioComSucessoERetornarUsuario() {
    Mockito.when(usuarioRepository.findByNomeUsuario("novoUsuario")).thenReturn(Optional.empty());
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Mockito.when(securityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);

    CriacaoUsuarioDTO criacaoDTO = new CriacaoUsuarioDTO("novoUsuario", "novaSenha", USUARIO);

    Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class)))
        .thenAnswer(
            invocation -> {
              Usuario usuarioSalvo = invocation.getArgument(0);
              usuarioSalvo.setId(UUID.randomUUID().toString());
              return usuarioSalvo;
            });

    UsuarioRespostaRetornaUsuarioOuMensagem resultado = usuarioService.criarUsuario(criacaoDTO);

    assertNotNull(resultado.id());
    assertEquals("novoUsuario", resultado.nomeUsuario());
    assertTrue(passwordEncoder.matches("novaSenha", resultado.senha()));
  }

  @Test
  public void naoDeveCriarUsuarioRetornandoMensagemDeUsuarioJaCadastrado() {
    Usuario usuario = new Usuario(null, "usuarioExistente", "senha", List.of());
    Mockito.when(usuarioRepository.findByNomeUsuario("usuarioExistente"))
        .thenReturn(Optional.of(usuario));

    CriacaoUsuarioDTO criacaoDTO = new CriacaoUsuarioDTO("usuarioExistente", "senha", USUARIO);

    UsuarioRespostaRetornaUsuarioOuMensagem resultado = usuarioService.criarUsuario(criacaoDTO);

    assertNull(resultado.id());
    assertEquals("Usuário já cadastrado. Tente fazer login.", resultado.mensagem());
  }

  @Test
  public void naoDeveCriarUsuarioRetornandoMensagemDeCredenciaisInvalidas() {
    CriacaoUsuarioDTO criacaoDTO = new CriacaoUsuarioDTO(null, null, null);

    UsuarioRespostaRetornaUsuarioOuMensagem resultado = usuarioService.criarUsuario(criacaoDTO);

    assertNull(resultado.id());
    assertEquals(
        "Não foi possível criar este usuário. Verifique os dados informados.",
        resultado.mensagem());
  }
}
