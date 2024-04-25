package com.pagsestagio.movieapi.model.resultado;

import com.pagsestagio.movieapi.model.Filme;

import java.util.List;


public record FilmeResultadoRetornaLista(List<Filme> listaDeFilmes, Exception excecao) implements FilmeResultado  {
}
