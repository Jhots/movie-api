package com.pagsestagio.movieapi.model;

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
          && Objects.equals(nomeFilme, filme.nomeFilme);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    if (id != null) {
      return Objects.hash(id);
    }
    return Objects.hash(idLegado, idPublico, nomeFilme);
  }
}
