package com.pagsestagio.movieapi.controller.resposta;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;

import java.util.List;

@Deprecated
public record FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(List<Filme> listaDeFilmes, String mensagemStatus) implements FilmeResposta {
}
