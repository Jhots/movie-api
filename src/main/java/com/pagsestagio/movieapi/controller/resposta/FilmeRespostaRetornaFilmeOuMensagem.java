package com.pagsestagio.movieapi.controller.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FilmeRespostaRetornaFilmeOuMensagem(
        @JsonProperty("idPublico") UUID idpublico,
        @JsonProperty("nomeFilme") String nomeFilme,
        @JsonProperty("sinopseFilme") String sinopseFilme,
        @JsonProperty("categoriaFilme") String categoriaFilme,
        @JsonProperty("anoFilme") Integer anoFilme,
        @JsonProperty("diretorFilme") String diretorFilme,
        @JsonProperty("mensagemStatus") String mensagemStatus)
    implements FilmeResposta {
    public FilmeRespostaRetornaFilmeOuMensagem(UUID idpublico, String nomeFilme, String sinopseFilme, String categoriaFilme, Integer anoFilme, String diretorFilme) {
        this(idpublico, nomeFilme, sinopseFilme, categoriaFilme, anoFilme, diretorFilme, null);
    }

    public FilmeRespostaRetornaFilmeOuMensagem(String mensagemStatus) {
        this(null, null, null, null, null, null, mensagemStatus);
    }
}
