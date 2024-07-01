package com.pagsestagio.movieapi.apiExterna.resposta;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmeRespostaApiExternaRetornaDadosFilme(
    @JsonProperty("Title") String nomeFilme,
    @JsonProperty("Plot") String sinopseFilme,
    @JsonProperty("Genre") String categoriaFilme,
    @JsonProperty("Year") Integer anoFilme,
    @JsonProperty("Director") String diretorFilme)
    implements FilmeRespostaApiExterna {}
