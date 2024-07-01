package com.pagsestagio.movieapi.apiExterna.omdb.service;

import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import com.pagsestagio.movieapi.apiExterna.omdb.client.OmdbClient;
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

  public FilmeRespostaApiExternaRetornaDadosFilme getFilmePorNome(String nome) {
    return omdbClient.getFilmePorNome(apiKey, nome);
  }
}
