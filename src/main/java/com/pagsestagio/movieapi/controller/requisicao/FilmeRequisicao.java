package com.pagsestagio.movieapi.controller.requisicao;

import com.pagsestagio.movieapi.model.Filme;

public record FilmeRequisicao(Integer identificador, String nomeFilme) {

    public FilmeRequisicao(Filme filme){
        this(filme.getId(), filme.getNome());

    }

}
