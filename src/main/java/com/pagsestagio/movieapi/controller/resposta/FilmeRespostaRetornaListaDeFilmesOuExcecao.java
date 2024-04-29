package com.pagsestagio.movieapi.controller.resposta;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;

import java.util.List;
public record FilmeRespostaRetornaListaDeFilmesOuExcecao(List<FilmeDTO> listaDeFilmes, String mensagemStatus) implements FilmeResposta {}