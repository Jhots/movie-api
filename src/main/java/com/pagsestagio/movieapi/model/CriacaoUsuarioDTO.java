package com.pagsestagio.movieapi.model;

import com.pagsestagio.movieapi.controller.resposta.UsuarioResposta;

public record CriacaoUsuarioDTO(String nomeUsuario, String senha, NomeFuncao funcao)
    implements UsuarioResposta {

    public boolean validaCriacaoUsuario() {
        return this.nomeUsuario() == null || this.senha() == null || this.funcao() == null;
    }
}
