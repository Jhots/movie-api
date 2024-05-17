package com.pagsestagio.movieapi.controller.resposta;

import com.pagsestagio.movieapi.model.FilmeDTOV1;

import java.util.List;

@Deprecated
public record FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(List<FilmeDTOV1> listaDeFilmes, String mensagemStatus) implements FilmeResposta {
}
