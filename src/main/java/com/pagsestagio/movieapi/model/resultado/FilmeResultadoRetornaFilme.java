package com.pagsestagio.movieapi.model.resultado;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.model.Filme;

public record FilmeResultadoRetornaFilme(Filme filme, Exception excecao) implements FilmeResultado {
}
