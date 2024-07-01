package com.pagsestagio.movieapi.apiExterna.omdb.client;

import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OmdbClient", url = "${omdb.api.url}")
public interface OmdbClient {

  @GetMapping
  FilmeRespostaApiExternaRetornaDadosFilme getFilmePorNome(
      @RequestParam("apikey") String apiKey, @RequestParam("t") String nome);
}
