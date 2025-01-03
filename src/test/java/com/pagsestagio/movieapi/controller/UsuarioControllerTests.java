package com.pagsestagio.movieapi.controller;

import static com.pagsestagio.movieapi.model.NomeFuncao.USUARIO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.IntegrationBaseTest;
import com.pagsestagio.movieapi.model.CriacaoUsuarioDTO;
import com.pagsestagio.movieapi.model.Funcao;
import com.pagsestagio.movieapi.model.LoginUsuarioDTO;
import com.pagsestagio.movieapi.model.NomeFuncao;
import com.pagsestagio.movieapi.model.Usuario;
import com.pagsestagio.movieapi.repository.UsuarioRepository;
import com.pagsestagio.movieapi.security.config.SecurityConfiguration;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class UsuarioControllerTests extends IntegrationBaseTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private UsuarioRepository usuarioRepository;
  @Autowired private SecurityConfiguration securityConfiguration;

  @BeforeEach
  public void setUp() {
    usuarioRepository.deleteAll();
  }

  @Test
  public void deveRetornarOkQuandoUsuarioForAutenticadoERetornarToken() throws Exception {
    Funcao funcao = new Funcao(null, USUARIO);

    Usuario usuario =
        new Usuario(
            null,
            "usuario_teste",
            securityConfiguration.passwordEncoder().encode("senha123"),
            List.of(funcao));

    usuarioRepository.save(usuario);

    LoginUsuarioDTO login = new LoginUsuarioDTO("usuario_teste", "senha123");

    var requisicao =
        post("/v2/usuarios/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(login));

    var resultado = mockMvc.perform(requisicao);

    resultado
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.mensagem").doesNotExist());
  }

  @Test
  public void deveRetornarBadRequestQuandoUsuarioNaoForAutenticadoERetornarMensagem()
      throws Exception {
    LoginUsuarioDTO login = new LoginUsuarioDTO("usuario_invalido", "senha_errada");

    var requisicao =
        post("/v2/usuarios/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(login));

    var resultado = mockMvc.perform(requisicao);

    resultado
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.mensagem").value("Usuário não cadastrado. Tente criar um usuário."))
        .andExpect(jsonPath("$.token").doesNotExist());
  }

  @Test
  public void deveRetornarOkQuandoUsuarioForCriadoERetornarUsuario() throws Exception {
    CriacaoUsuarioDTO criacaoUsuario =
        new CriacaoUsuarioDTO("novo_usuario", "senha123", NomeFuncao.USUARIO);

    var requisicao =
        post("/v2/usuarios/criar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(criacaoUsuario));

    var resultado = mockMvc.perform(requisicao);

    resultado
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andExpect(jsonPath("$.nomeUsuario").value("novo_usuario"))
        .andExpect(jsonPath("$.funcao").value("USUARIO"))
        .andExpect(jsonPath("$.mensagem").doesNotExist());
  }

  @Test
  public void deveRetornarBadRequestQuandoUsuarioNaoForCriadoERetornarMensagem() throws Exception {
    Funcao funcao = new Funcao(null, USUARIO);

    Usuario usuarioExistente =
        new Usuario(
            null,
            "usuario_duplicado",
            securityConfiguration.passwordEncoder().encode("senha123"),
            List.of(funcao));

    usuarioRepository.save(usuarioExistente);

    CriacaoUsuarioDTO criacaoUsuario =
        new CriacaoUsuarioDTO("usuario_duplicado", "senha123", NomeFuncao.USUARIO);

    var requisicao =
        post("/v2/usuarios/criar")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(criacaoUsuario));

    var resultado = mockMvc.perform(requisicao);

    resultado
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.mensagem").value("Usuário já cadastrado. Tente fazer login."))
        .andExpect(jsonPath("$.id").doesNotExist())
        .andExpect(jsonPath("$.nomeUsuario").doesNotExist());
  }
}
