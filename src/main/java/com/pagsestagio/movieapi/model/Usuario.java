package com.pagsestagio.movieapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "USERS")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "USER_ID")
  private String id;

  @Column(name = "USERNAME", unique = true)
  private String nomeUsuario;

  @Column(name = "PASSWORD")
  private String senha;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "USER_ROLES",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  private List<Funcao> funcoes;

  public Usuario() {}

  public Usuario(String id, String nomeUsuario, String senha, List<Funcao> funcoes) {
    this.id = id;
    this.nomeUsuario = nomeUsuario;
    this.senha = senha;
    this.funcoes = funcoes;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNomeUsuario() {
    return nomeUsuario;
  }

  public void setNomeUsuario(String nomeUsuario) {
    this.nomeUsuario = nomeUsuario;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public List<Funcao> getFuncoes() {
    return funcoes;
  }

  public void setFuncoes(List<Funcao> funcoes) {
    this.funcoes = funcoes;
  }
}
