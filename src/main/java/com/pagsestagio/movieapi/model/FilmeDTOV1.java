package com.pagsestagio.movieapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilmeDTOV1 {
  private Integer idLegado;
  @JsonProperty("Title")
  private String nomeFilme;

  public FilmeDTOV1(Integer idLegado, String nomeFilme) {
    this.idLegado = idLegado;
    this.nomeFilme = nomeFilme;
  }

  public Integer getIdLegado() {
    return idLegado;
  }

  public void setIdLegado(Integer idLegado) {
    this.idLegado = idLegado;
  }

  public String getNomeFilme() {
    return nomeFilme;
  }

  public void setNomeFilme(String nomeFilme) {
    this.nomeFilme = nomeFilme;
  }
}
