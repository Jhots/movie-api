package com.pagsestagio.movieapi.model.resultado;

import com.pagsestagio.movieapi.model.FilmeDTOV1;

import java.util.List;

@Deprecated
public record FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
    List<FilmeDTOV1> listaDeFilmes, String mensagemStatus) implements FilmeResultado {}
