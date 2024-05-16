package com.pagsestagio.movieapi.model.resultado;

import com.pagsestagio.movieapi.model.Filme;

import java.util.List;
@Deprecated
public record FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(List<Filme> listaDeFilmes, String mensagemStatus) implements FilmeResultado{
}
