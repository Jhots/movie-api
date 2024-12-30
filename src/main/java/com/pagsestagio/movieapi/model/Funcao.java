package com.pagsestagio.movieapi.model;

import jakarta.persistence.*;

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

  public NomeFuncao getNome() {
    return nome;
  }
}
