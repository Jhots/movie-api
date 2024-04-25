package com.pagsestagio.movieapi.controller.resposta;

import com.pagsestagio.movieapi.model.Filme;

import java.util.List;
public record FilmeRespostaSucessoRetornaLista(List<Filme> listaDeFilmes, Exception excecao) implements FilmeResposta {}