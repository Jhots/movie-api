package com.pagsestagio.movieapi.controller.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioRespostaRetornaTokenOuMensagem(
    @JsonProperty("token") String token, @JsonProperty("mensagem") String mensagem)
    implements UsuarioResposta {

    public static UsuarioRespostaRetornaTokenOuMensagem usuarioRespostaRetornaToken(String token) {
        return new UsuarioRespostaRetornaTokenOuMensagem(token, null);
    }

    public static UsuarioRespostaRetornaTokenOuMensagem usuarioRespostaRetornaMensagem(String mensagem) {
        return new UsuarioRespostaRetornaTokenOuMensagem(null, mensagem);
    }
}
