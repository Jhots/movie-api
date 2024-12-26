package com.pagsestagio.movieapi.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.EventoFilmePesquisado;
import com.pagsestagio.movieapi.model.FilmeEstatistica;
import com.pagsestagio.movieapi.repository.FilmeEstatisticaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FilmePesquisadoConsumer {

    private final FilmeEstatisticaRepository filmeEstatisticaRepository;

    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(FilmePesquisadoConsumer.class);

    public FilmePesquisadoConsumer(FilmeEstatisticaRepository filmeEstatisticaRepository, ObjectMapper objectMapper) {
        this.filmeEstatisticaRepository = filmeEstatisticaRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "movie-api.filme-pesquisado", groupId = "filme-estatistica-group")
    public void consomeMensagem(@Payload String mensagem, Acknowledgment ack) throws JsonProcessingException {
        logger.info("Mensagem recebida: {}", mensagem);

        var evento = objectMapper.readValue(mensagem, EventoFilmePesquisado.class);

        try {
            UUID idPublico = UUID.fromString(evento.idPublico());
            String nomeFilme = evento.nomeFilme();

            Optional<FilmeEstatistica> filmeEstatisticaOptional = filmeEstatisticaRepository.findById(idPublico);

            if (filmeEstatisticaOptional.isPresent()) {
                FilmeEstatistica estatistica = filmeEstatisticaOptional.get();
                estatistica.incrementarContador();
                filmeEstatisticaRepository.save(estatistica);
                logger.info("Contador incrementado para o filme: {} (ID: {})", nomeFilme, idPublico);
            } else {
                FilmeEstatistica novaEstatistica = new FilmeEstatistica(idPublico, nomeFilme, 1);
                filmeEstatisticaRepository.save(novaEstatistica);
                logger.info("Nova estatística criada para o filme: {} (ID: {})", nomeFilme, idPublico);
            }

            ack.acknowledge();

        } catch (Exception e) {
            logger.error("Erro ao processar evento de filme pesquisado: {}", mensagem);
            e.printStackTrace();
        }
    }
}
