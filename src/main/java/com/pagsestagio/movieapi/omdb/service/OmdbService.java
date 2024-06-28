package com.pagsestagio.movieapi.omdb.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.omdb.client.OmdbClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OmdbService {

    @Value("${omdb.api.key}")
    private String apiKey;

    private final OmdbClient omdbClient;

    public OmdbService(OmdbClient omdbClient) {
        this.omdbClient = omdbClient;
    }

    public Filme getFilmePorNome(String nome) {
        return omdbClient.getFilmePorNome(apiKey, nome);
    }
}
