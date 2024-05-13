package com.pagsestagio.movieapi.oldversion.controller.resposta;

import com.pagsestagio.movieapi.oldversion.model.FilmeDTOOldVersion;

import java.util.List;

@Deprecated
public record FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(List<FilmeDTOOldVersion> listaDeFilmes, String mensagemStatus) implements FilmeRespostaOldVersion {
}
