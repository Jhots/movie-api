package com.pagsestagio.movieapi.oldversion.controller.requisicao;

import com.pagsestagio.movieapi.oldversion.model.FilmeOldVersion;

@Deprecated
public record FilmeRequisicaoOldVersion(Integer identificador, String nomeFilme) {

    public FilmeRequisicaoOldVersion(FilmeOldVersion filme){
        this(filme.identificador(), filme.nomeFilme());
    }

}
