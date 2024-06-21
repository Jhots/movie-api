package com.pagsestagio.movieapi.model;

import java.util.UUID;

public class FilmeDTOV2 {
  private Integer id;
  private Integer idLegado;
  private UUID idPublico;
  private String nomeFilme;
  private String sinopseFilme;
  private String categoriaFilme;
  private Integer anoFilme;
  private String diretorFilme;

  public FilmeDTOV2(Integer id, Integer idLegado, UUID idPublico, String nomeFilme) {
    this.id = id;
    this.idLegado = idLegado;
    this.idPublico = idPublico;
    this.nomeFilme = nomeFilme;
  }

  public FilmeDTOV2(
      Integer id,
      Integer idLegado,
      UUID idPublico,
      String nomeFilme,
      String sinopseFilme,
      String categoriaFilme,
      Integer anoFilme,
      String diretorFilme) {
    this.id = id;
    this.idLegado = idLegado;
    this.idPublico = idPublico;
    this.nomeFilme = nomeFilme;
    this.sinopseFilme = sinopseFilme;
    this.categoriaFilme = categoriaFilme;
    this.anoFilme = anoFilme;
    this.diretorFilme = diretorFilme;
  }

  public FilmeDTOV2() {}

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

  public String getSinopseFilme() {
    return sinopseFilme;
  }

  public void setSinopseFilme(String sinopseFilme) {
    this.sinopseFilme = sinopseFilme;
  }

  public String getCategoriaFilme() {
    return categoriaFilme;
  }

  public void setCategoriaFilme(String categoriaFilme) {
    this.categoriaFilme = categoriaFilme;
  }

  public Integer getAnoFilme() {
    return anoFilme;
  }

  public void setAnoFilme(Integer anoFilme) {
    this.anoFilme = anoFilme;
  }

  public String getDiretorFilme() {
    return diretorFilme;
  }

  public void setDiretorFilme(String diretorFilme) {
    this.diretorFilme = diretorFilme;
  }
}
