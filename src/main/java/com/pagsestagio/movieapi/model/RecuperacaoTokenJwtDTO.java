package com.pagsestagio.movieapi.model;

import com.pagsestagio.movieapi.controller.resposta.UsuarioResposta;

public record RecuperacaoTokenJwtDTO(String token) implements UsuarioResposta {}
