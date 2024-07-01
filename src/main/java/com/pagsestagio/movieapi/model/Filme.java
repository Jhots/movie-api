package com.pagsestagio.movieapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "MOVIE")
public class Filme {
  public Filme(Integer id, Integer idLegado, UUID idPublico, String nomeFilme) {
    this.id = id;
    this.idLegado = idLegado;
    this.idPublico = UUID.randomUUID();
    this.nomeFilme = nomeFilme;
  }

  public Filme(
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
    this.idPublico = UUID.randomUUID();
    this.nomeFilme = nomeFilme;
    this.sinopseFilme = sinopseFilme;
    this.categoriaFilme = categoriaFilme;
    this.anoFilme = anoFilme;
    this.diretorFilme = diretorFilme;
  }

  public Filme() {
    this.idPublico = UUID.randomUUID();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "MOVIE_ID")
  private Integer id;

  @Column(name = "LEGACY_ID")
  private Integer idLegado;

  @Column(name = "PUBLIC_ID")
  @JdbcTypeCode(Types.VARCHAR)
  private UUID idPublico;

  @Column(name = "MOVIE_NAME")
  private String nomeFilme;

  @Column(name = "MOVIE_SYNOPSIS")
  private String sinopseFilme;

  @Column(name = "MOVIE_CATEGORY")
  private String categoriaFilme;

  @Column(name = "MOVIE_YEAR")
  private Integer anoFilme;

  @Column(name = "MOVIE_DIRECTOR")
  private String diretorFilme;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Filme filme = (Filme) o;

    if (id != null && filme.id != null) {
      return Objects.equals(id, filme.id);
    } else if (id == null && filme.id == null) {
      return Objects.equals(idLegado, filme.idLegado)
          && Objects.equals(idPublico, filme.idPublico)
          && Objects.equals(nomeFilme, filme.nomeFilme)
          && Objects.equals(sinopseFilme, filme.sinopseFilme)
          && Objects.equals(categoriaFilme, filme.categoriaFilme)
          && anoFilme == filme.anoFilme
          && Objects.equals(diretorFilme, filme.diretorFilme);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return Objects.hash(
        idLegado, idPublico, nomeFilme, sinopseFilme, categoriaFilme, anoFilme, diretorFilme);
  }
}
