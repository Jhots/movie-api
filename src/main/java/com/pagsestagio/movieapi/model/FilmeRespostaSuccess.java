package com.pagsestagio.movieapi.model;

import java.util.Map;

public record FilmeRespostaSuccess(Map<Integer, String> listaDeFilmes) implements FilmeResposta{}