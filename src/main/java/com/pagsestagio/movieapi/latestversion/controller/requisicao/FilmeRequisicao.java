package com.pagsestagio.movieapi.latestversion.controller.requisicao;

import com.pagsestagio.movieapi.latestversion.model.Filme;

public record FilmeRequisicao(Integer identificador, String nomeFilme) {

    public FilmeRequisicao(Filme filme){
        this(filme.getId(), filme.getNome());

    }

}
