package com.pagsestagio.movieapi.controller.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioRespostaRetornaUsuarioOuMensagem(
    @JsonProperty("id") String id,
    @JsonProperty("nomeUsuario") String nomeUsuario,
    @JsonProperty("senha") String senha,
    @JsonProperty("funcao") String funcao,
    @JsonProperty("mensagem") String mensagem)
    implements UsuarioResposta {
  public UsuarioRespostaRetornaUsuarioOuMensagem(
      String id, String nomeUsuario, String senha, String funcao) {
    this(id, nomeUsuario, senha, funcao, null);
  }

  public UsuarioRespostaRetornaUsuarioOuMensagem(String mensagem) {
    this(null, null, null, null, mensagem);
  }
}
