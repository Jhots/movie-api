package com.pagsestagio.movieapi.apiExterna.omdb.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.model.HttpResponse.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.IntegrationBaseTest;
import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OmdbClientTests extends IntegrationBaseTest {

  @Autowired private OmdbClient omdbClient;

  private static MockServerClient serverClient;

  private ObjectMapper mapper = new ObjectMapper();

  @Value("${omdb.api.key}")
  private String apiKey;

  @BeforeAll
  public static void start() {
    serverClient = ClientAndServer.startClientAndServer(4000);
  }

  @BeforeEach
  public void reset() {
    serverClient.reset();
  }

  @AfterAll
  public static void stop() {
    serverClient.stop();
  }

  @Test
  void deveRetornarDadosDoFilmeAtravesDoNome() throws JsonProcessingException {

    String nomeFilme = "Inception";

    var filmeExterno =
        new FilmeRespostaApiExternaRetornaDadosFilme(
            "Inception",
            "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
            "Action, Adventure, Sci-Fi",
            2010,
            "Christopher Nolan");

    String filmeEmJson = mapper.writeValueAsString(filmeExterno);

    serverClient
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withQueryStringParameter("apiKey", apiKey)
                .withQueryStringParameter("t", nomeFilme))
        .respond(
            response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(filmeEmJson));

    FilmeRespostaApiExternaRetornaDadosFilme resultado =
        omdbClient.getFilmePorNome(apiKey, nomeFilme);

    assertEquals("Inception", resultado.nomeFilme());
    assertEquals(2010, resultado.anoFilme());
    assertEquals("Christopher Nolan", resultado.diretorFilme());
    assertEquals(
        "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
        resultado.sinopseFilme());
    assertEquals("Action, Adventure, Sci-Fi", resultado.categoriaFilme());
  }
}
