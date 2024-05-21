package com.pagsestagio.movieapi.model;

import java.util.UUID;

public class FilmeDTOV2 {
  private Integer id;
  private Integer idLegado;
  private UUID idPublico;
  private String nomeFilme;

  public FilmeDTOV2(Integer id, Integer idLegado, UUID idPublico, String nomeFilme) {
    this.id = id;
    this.idLegado = idLegado;
    this.idPublico = idPublico;
    this.nomeFilme = nomeFilme;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdLegado() {
    return idLegado;
  }

  public void setIdLegado(Integer idLegado) {
    this.idLegado = idLegado;
  }

  public UUID getIdPublico() {
    return idPublico;
  }

  public void setIdPublico(UUID idPublico) {
    this.idPublico = idPublico;
  }

  public String getNomeFilme() {
    return nomeFilme;
  }

  public void setNomeFilme(String nomeFilme) {
    this.nomeFilme = nomeFilme;
  }
}
