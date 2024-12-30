package com.pagsestagio.movieapi.model;

public enum NomeFuncao {
  USUARIO,
  ADMINISTRADOR;

  @Override
  public String toString() {
    return "ROLE_" + this.name();
  }
}
