package com.pagsestagio.movieapi.controller.resposta;

import java.util.Map;

public record FilmeRespostaSucessoRetornaLista(Map<Integer, String> listaDeFilmes) implements FilmeResposta {}