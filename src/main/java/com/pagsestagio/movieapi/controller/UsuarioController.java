package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.UsuarioResposta;
import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaTokenOuMensagem;
import com.pagsestagio.movieapi.controller.resposta.UsuarioRespostaRetornaUsuarioOuMensagem;
import com.pagsestagio.movieapi.model.CriacaoUsuarioDTO;
import com.pagsestagio.movieapi.model.LoginUsuarioDTO;
import com.pagsestagio.movieapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/usuarios")
public class UsuarioController {

  @Autowired private UsuarioService usuarioService;

  @PostMapping("/login")
  public ResponseEntity<UsuarioResposta> autenticarUsuario(
      @RequestBody LoginUsuarioDTO loginUsuarioDTO) {
    UsuarioRespostaRetornaTokenOuMensagem retornoService =
        usuarioService.autenticarUsuario(loginUsuarioDTO);

    ResponseEntity<UsuarioResposta> respostaRequisicao = null;

    if (retornoService.mensagem() == null) {
      respostaRequisicao =
          ResponseEntity.ok()
              .body(new UsuarioRespostaRetornaTokenOuMensagem(retornoService.token(), null));
    } else {
      respostaRequisicao =
          ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new UsuarioRespostaRetornaUsuarioOuMensagem(retornoService.mensagem()));
    }

    return respostaRequisicao;
  }

  @PostMapping("/criar")
  public ResponseEntity<UsuarioResposta> criarUsuario(
      @RequestBody CriacaoUsuarioDTO criacaoUsuarioDTO) {
    UsuarioRespostaRetornaUsuarioOuMensagem retornoService =
        usuarioService.criarUsuario(criacaoUsuarioDTO);

    ResponseEntity<UsuarioResposta> respostaRequisicao = null;

    if (retornoService.mensagem() == null) {
      respostaRequisicao =
          ResponseEntity.ok()
              .body(
                  new UsuarioRespostaRetornaUsuarioOuMensagem(
                      retornoService.id(),
                      retornoService.nomeUsuario(),
                      retornoService.senha(),
                      retornoService.funcao()));
    } else {
      respostaRequisicao =
          ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new UsuarioRespostaRetornaUsuarioOuMensagem(retornoService.mensagem()));
    }

    return respostaRequisicao;
  }
}
