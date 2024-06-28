package com.pagsestagio.movieapi.omdb.client;

import com.pagsestagio.movieapi.model.Filme;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OmdbClient", url = "${omdb.api.url}")
public interface OmdbClient {

  @GetMapping
  Filme getFilmePorNome(
      @RequestParam("apikey") String apiKey, @RequestParam("t") String nome);
}
