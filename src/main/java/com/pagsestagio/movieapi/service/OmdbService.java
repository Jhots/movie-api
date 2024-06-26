package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OmdbService {
    @Value("${omdb.api.key}")
    private String apiKey;
    private static final String URL_BASE = "http://www.omdbapi.com/";

    private RestTemplate restTemplate = new RestTemplate();

    public Filme getFilmePorNome(String nome){
        String url = URL_BASE + "?apikey=" + apiKey + "&t=" + nome;
        return restTemplate.getForObject(url, Filme.class);
    }
}
