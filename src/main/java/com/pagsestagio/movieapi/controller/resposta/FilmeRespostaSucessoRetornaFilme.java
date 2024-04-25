package com.pagsestagio.movieapi.controller.resposta;

import com.pagsestagio.movieapi.model.Filme;

public record FilmeRespostaSucessoRetornaFilme(Filme filme, Exception excecao) implements FilmeResposta {}