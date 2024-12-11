package com.pagsestagio.movieapi.kafka;

import com.pagsestagio.movieapi.apiExterna.omdb.service.OmdbService;
import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmeConsumer {

    private final OmdbService omdbService;
    private final FilmeRepository filmeRepository;

    public FilmeConsumer(OmdbService omdbService, FilmeRepository filmeRepository) {
        this.omdbService = omdbService;
        this.filmeRepository = filmeRepository;
    }

    @KafkaListener(topics = "movie-api.informacoes-adicionais-filmeMOVIE_OUTBOX")
    public void consomeMensagem(@Payload String mensagem, Acknowledgment ack) {
        System.out.println("Mensagem recebida: " + mensagem);

        try {
            FilmeRespostaApiExternaRetornaDadosFilme filmeDaApiExterna = omdbService.getFilmePorNome(mensagem);

            Optional<Filme> filmeNoBanco = filmeRepository.findByNomeFilme(mensagem);

            if (filmeNoBanco.isPresent()) {
                Filme filme = filmeNoBanco.get();

                filme.setSinopseFilme(filmeDaApiExterna.sinopseFilme());
                filme.setCategoriaFilme(filmeDaApiExterna.categoriaFilme());
                filme.setAnoFilme(filmeDaApiExterna.anoFilme());
                filme.setDiretorFilme(filmeDaApiExterna.diretorFilme());

                filmeRepository.save(filme);

                System.out.println("Filme atualizado com sucesso: " + filme.getNomeFilme());
            } else {
                System.err.println("Filme não encontrado no banco: " + mensagem);
            }

            ack.acknowledge();

        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem: " + mensagem);
            e.printStackTrace();

        }
    }
}