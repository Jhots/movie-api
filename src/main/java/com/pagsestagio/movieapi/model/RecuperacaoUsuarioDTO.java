package com.pagsestagio.movieapi.model;

import java.util.List;

public record RecuperacaoUsuarioDTO(Long id, String nomeUsuario, List<Funcao> funcoes) {}
