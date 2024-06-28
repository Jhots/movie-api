package com.pagsestagio.movieapi.client;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.omdb.client.OmdbClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OmdbClientTests {

  @Autowired private OmdbClient omdbClient;
  private ClientAndServer omdbService;

  @Value("${omdb.api.key}")
  private String apiKey;

  @BeforeAll
  public void start() {
    omdbService = ClientAndServer.startClientAndServer(4000);
  }

  @BeforeEach
  public void reset() {
    omdbService.reset();
  }

  @AfterAll
  void stop() {
    omdbService.stop();
  }

  @Test
  void deveRetornarDadosDoFilmeAtravesDoNome() {

    String nomeFilme = "Inception";

    omdbService
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withQueryStringParameter("apiKey", apiKey)
                .withQueryStringParameter("t", nomeFilme))
        .respond(
            response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"Title\":\"Inception\",\"Year\":\"2010\",\"Director\":\"Christopher Nolan\",\"Plot\":\"A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.\",\"Genre\":\"Action, Adventure, Sci-Fi\"}"));

    Filme resultado = omdbClient.getFilmePorNome(apiKey, nomeFilme);

    assertEquals("Inception", resultado.getNomeFilme());
    assertEquals("2010", resultado.getAnoFilme());
    assertEquals("Christopher Nolan", resultado.getDiretorFilme());
    assertEquals(
        "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
        resultado.getSinopseFilme());
    assertEquals("Action, Adventure, Sci-Fi", resultado.getCategoriaFilme());
  }
}