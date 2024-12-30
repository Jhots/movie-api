package com.pagsestagio.movieapi.model;

import com.pagsestagio.movieapi.controller.resposta.UsuarioResposta;

public record LoginUsuarioDTO(String nomeUsuario, String senha) implements UsuarioResposta {}
