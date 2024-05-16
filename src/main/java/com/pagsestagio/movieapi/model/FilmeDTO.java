package com.pagsestagio.movieapi.model;

import java.util.UUID;

public record FilmeDTO(Integer id, Integer idLegado, UUID idPublico, String nomeFilme) {
}
