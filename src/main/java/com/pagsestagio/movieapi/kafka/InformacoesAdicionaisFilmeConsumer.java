package com.pagsestagio.movieapi.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.apiExterna.omdb.service.OmdbService;
import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeOutboxPayload;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InformacoesAdicionaisFilmeConsumer {

    private final OmdbService omdbService;
    private final FilmeRepository filmeRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(InformacoesAdicionaisFilmeConsumer.class);

    public InformacoesAdicionaisFilmeConsumer(OmdbService omdbService, FilmeRepository filmeRepository) {
        this.omdbService = omdbService;
        this.filmeRepository = filmeRepository;
    }

    @KafkaListener(topics = "movie-api.informacoes-adicionais-filmeMOVIE_OUTBOX", groupId = "${spring.kafka.consumer.group-id}")
    public void consomeMensagem(@Payload String mensagem, Acknowledgment ack) throws JsonProcessingException {
        logger.info("Mensagem recebida: " + mensagem);

        var filmeOutbox = objectMapper.readValue(mensagem, FilmeOutboxPayload.class);

        try {
            FilmeRespostaApiExternaRetornaDadosFilme filmeDaApiExterna = omdbService.getFilmePorNome(filmeOutbox.nomeFilme());

            Optional<Filme> filmeNoBanco = filmeRepository.findByNomeFilme(filmeOutbox.nomeFilme());

            if (filmeNoBanco.isPresent()) {
                Filme filme = filmeNoBanco.get();

                filme.setSinopseFilme(filmeDaApiExterna.sinopseFilme());
                filme.setCategoriaFilme(filmeDaApiExterna.categoriaFilme());
                filme.setAnoFilme(filmeDaApiExterna.anoFilme());
                filme.setDiretorFilme(filmeDaApiExterna.diretorFilme());

                filmeRepository.save(filme);

                logger.info("Filme atualizado com sucesso: " + filme.getNomeFilme());
            } else {
                logger.warn("Filme não encontrado no banco: " + mensagem);
            }

            ack.acknowledge();

        } catch (Exception e) {
            logger.error("Erro ao processar mensagem: " + mensagem);
            e.printStackTrace();

        }
    }
}