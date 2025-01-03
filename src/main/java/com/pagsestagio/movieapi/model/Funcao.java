package com.pagsestagio.movieapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Funcao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ROLE_ID")
  private Long id;

  @Column(name = "ROLE_NAME")
  @Enumerated(EnumType.STRING)
  private NomeFuncao nome;

  public Funcao() {}

  public Funcao(Long id, NomeFuncao nome) {
    this.id = id;
    this.nome = nome;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public NomeFuncao getNome() {
    return nome;
  }

  public void setNome(NomeFuncao nome) {
    this.nome = nome;
  }
}
