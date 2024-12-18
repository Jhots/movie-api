package com.pagsestagio.movieapi.model;

import java.time.LocalDateTime;

public record EventoFilmePesquisado(
        String nomeFilme, String idPublico, String timestamp
) {
    public EventoFilmePesquisado(String nomeFilme, String idPublico) {
        this(nomeFilme, idPublico, LocalDateTime.now().toString());
    }
}