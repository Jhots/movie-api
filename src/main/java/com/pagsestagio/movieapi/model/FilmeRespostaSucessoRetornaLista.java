package com.pagsestagio.movieapi.model;

import java.util.Map;

public record FilmeRespostaSucessoRetornaLista(Map<Integer, String> listaDeFilmes) implements FilmeResposta{}