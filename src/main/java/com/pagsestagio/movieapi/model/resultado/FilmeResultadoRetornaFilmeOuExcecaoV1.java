package com.pagsestagio.movieapi.model.resultado;

@Deprecated
public record FilmeResultadoRetornaFilmeOuExcecaoV1(
    Integer id, String nomeFilme, String mensagemStatus) implements FilmeResultado {}
