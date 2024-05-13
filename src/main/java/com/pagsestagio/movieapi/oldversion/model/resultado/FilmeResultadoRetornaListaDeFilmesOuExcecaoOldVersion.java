package com.pagsestagio.movieapi.oldversion.model.resultado;

import com.pagsestagio.movieapi.oldversion.model.FilmeDTOOldVersion;

import java.util.List;

@Deprecated
public record FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(List<FilmeDTOOldVersion> listaDeFilmes, String mensagemStatus) implements FilmeResultadoOldVersion {
}
