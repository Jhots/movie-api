package com.pagsestagio.movieapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeOutbox;
import com.pagsestagio.movieapi.model.FilmeOutboxPayload;
import com.pagsestagio.movieapi.repository.FilmeOutboxRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FilmeOutboxService {

    private static final String TOPIC_DESTINO = "movie-api.informacoes-adicionais-filmeMOVIE_OUTBOX";

    private final FilmeOutboxRepository filmeOutboxRepository;
    private final ObjectMapper objectMapper;


    public FilmeOutboxService(FilmeOutboxRepository filmeOutboxRepository, ObjectMapper objectMapper) {
        this.filmeOutboxRepository = filmeOutboxRepository;
        this.objectMapper = objectMapper;
    }

    public void salvaEventoNaTabelaDeOutbox(Filme filme) {
        try {
            FilmeOutbox filmeOutbox = new FilmeOutbox();
            filmeOutbox.setKey(filme.getIdPublico().toString());

            FilmeOutboxPayload objeto = new FilmeOutboxPayload(filme.getNomeFilme());
            String payload = objectMapper.writeValueAsString(objeto);
            filmeOutbox.setPayload(payload);

            filmeOutbox.setCriadoEm(LocalDateTime.now());
            filmeOutbox.setTopicoDestino(TOPIC_DESTINO);
            filmeOutbox.setProcessado(false);

            filmeOutboxRepository.save(filmeOutbox);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar evento na tabela de outbox", e);
        }
    }


}
