package com.pagsestagio.movieapi.model.resultado;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;

import java.util.List;


public record FilmeResultadoRetornaListaDeFilmesOuExcecao(List<FilmeDTO> listaDeFilmes, String mensagemStatus) implements FilmeResultado  {
}
