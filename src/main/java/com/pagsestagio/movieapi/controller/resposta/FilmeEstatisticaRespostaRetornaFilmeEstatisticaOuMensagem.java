package com.pagsestagio.movieapi.controller.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(
    @JsonProperty("idPublico") UUID idPublico,
    @JsonProperty("nomeFilme") String nomeFilme,
    @JsonProperty("contadorBuscas") Integer contadorBuscas,
    @JsonProperty("mensagemStatus") String mensagemStatus)
    implements FilmeEstatisticaResposta {
  public FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(
      UUID idPublico, String nomeFilme, Integer contadorBuscas) {
    this(idPublico, nomeFilme, contadorBuscas, null);
  }

  public FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(String mensagemStatus) {
    this(null, null, null, mensagemStatus);
  }
}
