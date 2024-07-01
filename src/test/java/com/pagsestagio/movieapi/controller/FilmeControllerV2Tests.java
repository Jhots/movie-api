package com.pagsestagio.movieapi.controller;

import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.IntegrationBaseTest;
import com.pagsestagio.movieapi.apiExterna.omdb.client.OmdbClient;
import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class FilmeControllerV2Tests extends IntegrationBaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired FilmeRepository filmeRepository;

  @Autowired private OmdbClient omdbClient;
  private static MockServerClient serverClient;

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

  UUID uuidNaoPresenteNoBanco = UUID.fromString("f1ea8127-3766-470d-900a-0dc692fb1741");
  UUID uuidFilme = UUID.fromString("8bec4f7d-0dc5-4a93-a704-ccb44cc6fa2f");
  String nomeFilme = "Avatar";
  String sinopseFilme =
      "Um ex-fuzileiro naval paraplégico é despachado para a lua Pandora em uma missão única.";
  String categoriaFilme = "Ficção Científica";
  Integer anoFilme = 2009;
  String diretorFilme = "James Cameron";

  FilmeDTOV2 filmeCompleto =
      new FilmeDTOV2(
          null, null, uuidFilme, nomeFilme, sinopseFilme, categoriaFilme, anoFilme, diretorFilme);
  FilmeDTOV2 filmeCompletoExcetoNome =
      new FilmeDTOV2(
          null, null, uuidFilme, null, sinopseFilme, categoriaFilme, anoFilme, diretorFilme);
  FilmeDTOV2 filmeSomenteNome = new FilmeDTOV2(null, null, null, "Avatar");
  FilmeDTOV2 filmeSemNomeEIdentificador = new FilmeDTOV2(null, null, null, null);

  @BeforeEach
  public void setUp() throws Exception {
    filmeRepository.deleteAll();
  }

  @Test
  void deveRetornarOkQuandoFilmeDaRequisicaoPossuiSomenteNome() throws Exception {

    FilmeRespostaApiExternaRetornaDadosFilme filmeDaApiExterna =
        new FilmeRespostaApiExternaRetornaDadosFilme(
            "Avatar",
            "Um filme sobre um mundo alienígena.",
            "Ação, Aventura, Ficção Científica",
            2009,
            "James Cameron");

    String filmeDaApiExternaJson = objectMapper.writeValueAsString(filmeDaApiExterna);

    serverClient
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withQueryStringParameter("apiKey", apiKey)
                .withQueryStringParameter("t", "Avatar"))
        .respond(
            response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(filmeDaApiExternaJson));

    FilmeDTOV2 filmeSomenteNome = new FilmeDTOV2(null, null, null, "Avatar");
    var requisicaoDeFilmeSomenteNome =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeSomenteNome));

    mockMvc.perform(requisicaoDeFilmeSomenteNome).andExpect(status().isOk());
  }

  @Test
  void deveRetornarOkQuandoFilmeDaResquisicaoECompleto() throws Exception {
    String filmeCompletoNome = filmeCompleto.getNomeFilme();
    String filmeCompletoJson =
        objectMapper.writeValueAsString(
            new FilmeRespostaApiExternaRetornaDadosFilme(
                filmeCompletoNome,
                filmeCompleto.getSinopseFilme(),
                filmeCompleto.getCategoriaFilme(),
                filmeCompleto.getAnoFilme(),
                filmeCompleto.getDiretorFilme()));

    serverClient
        .when(
            HttpRequest.request()
                .withMethod("GET")
                .withQueryStringParameter("apiKey", apiKey)
                .withQueryStringParameter("t", filmeCompletoNome))
        .respond(
            response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(filmeCompletoJson));

    var requisicaoDeFilmeCompleto =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeCompleto));
    mockMvc.perform(requisicaoDeFilmeCompleto).andExpect(status().isOk());
  }

  @Test
  void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiNomeNulo() throws Exception {

    var requisicaoDeFilmeComNomeNulo =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeSemNomeEIdentificador));

    var requisicaoDeFilmeComplexoExcetoNome =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeCompletoExcetoNome));

    var resultadoRequisicaoFilmeComNomeNulo = mockMvc.perform(requisicaoDeFilmeComNomeNulo);

    var resultadoRequisicaoFilmeComplexoExcetoNome =
        mockMvc.perform(requisicaoDeFilmeComplexoExcetoNome);

    resultadoRequisicaoFilmeComNomeNulo.andExpect(status().isBadRequest());
    resultadoRequisicaoFilmeComplexoExcetoNome.andExpect(status().isBadRequest());
  }

  @Test
  void deveRetornarBadRequestQuandoFilmeDaRequisicaoJaExiste() throws Exception {

    Filme filme = new Filme();
    filme.setIdPublico(uuidFilme);
    filme.setNomeFilme(nomeFilme);
    filmeRepository.save(filme);

    var requisicaoDeFilmeComIdentificadorJaExistente =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeCompleto));

    var resultadoRequisicaoDeFilmeComIdentificadorJaExistente =
        mockMvc.perform(requisicaoDeFilmeComIdentificadorJaExistente);

    resultadoRequisicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isBadRequest());
  }

  @Test
  void deveRetornarOkQuandoRequisicaoSolicitarUmIdentificadorCadastrado() throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeRepository.save(filmeNoBanco);

    var requisicaoDeFilmeAtravesDeIdentificador =
        get("/v2/filmes/{idPublico}", filmeNoBanco.getIdPublico());

    var resultadoRequisicaoDeFilmeAtravesDeIdentificador =
        mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificador);

    resultadoRequisicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());
  }

  @Test
  void deveRetornarNotFoundQuandoRequisicaoSolicitarUmIdentificadorNaoCadastrado()
      throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeRepository.save(filmeNoBanco);

    var requisicaoDeFilmeAtravesDeIdentificadorNaoExistente =
        get("/v2/filmes/{uuidNaoPresenteNoBanco}", uuidNaoPresenteNoBanco);

    var resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente =
        mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificadorNaoExistente);

    resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());
  }

  @Test
  void deveRetornarOkQuandoRequisicaoSolicitarDeletarUmIdentificadorCadastrado() throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeRepository.save(filmeNoBanco);

    var requisicaoDeFilmeAtravesDeIdentificador =
        delete("/v2/filmes/{idPublico}", filmeNoBanco.getIdPublico());

    var resultadoRequisicaoDeFilmeAtravesDeIdentificador =
        mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificador);

    resultadoRequisicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());
  }

  @Test
  void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado()
      throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeRepository.save(filmeNoBanco);

    var requisicaoDeFilmeAtravesDeIdentificadorNaoExistente =
        delete("/v2/filmes/{uuidNaoPresenteNoBanco}", uuidNaoPresenteNoBanco);

    var resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente =
        mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificadorNaoExistente);

    resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());
  }

  @Test
  void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarFilmeSomenteComNomeNulo()
      throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeNoBanco.setSinopseFilme(sinopseFilme);
    filmeNoBanco.setCategoriaFilme(categoriaFilme);
    filmeNoBanco.setAnoFilme(anoFilme);
    filmeNoBanco.setDiretorFilme(diretorFilme);
    filmeRepository.save(filmeNoBanco);

    var requisicaoDeFilmeComNomeNulo =
        put("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeSemNomeEIdentificador));

    var resultadoRequisicaoDeFilmeComNomeNulo = mockMvc.perform(requisicaoDeFilmeComNomeNulo);

    resultadoRequisicaoDeFilmeComNomeNulo.andExpect(status().isBadRequest());
  }

  @Test
  void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme()
      throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeNoBanco.setSinopseFilme(sinopseFilme);
    filmeNoBanco.setCategoriaFilme(categoriaFilme);
    filmeNoBanco.setAnoFilme(anoFilme);
    filmeNoBanco.setDiretorFilme(diretorFilme);
    filmeRepository.save(filmeNoBanco);

    FilmeDTOV2 filmeAtualizado = new FilmeDTOV2(null, null, filmeNoBanco.getIdPublico(), nomeFilme);

    var requisicaoDeFilmeComIdentificadorJaExistente =
        put("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeAtualizado));

    var resultadoRequisicaoDeFilmeComIdentificadorJaExistente =
        mockMvc.perform(requisicaoDeFilmeComIdentificadorJaExistente);

    resultadoRequisicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isOk());
  }

  @Test
  void
      deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes()
          throws Exception {

    Filme filmeNoBanco = new Filme();
    filmeNoBanco.setIdPublico(uuidFilme);
    filmeNoBanco.setNomeFilme(nomeFilme);
    filmeNoBanco.setSinopseFilme(sinopseFilme);
    filmeNoBanco.setCategoriaFilme(categoriaFilme);
    filmeNoBanco.setAnoFilme(anoFilme);
    filmeNoBanco.setDiretorFilme(diretorFilme);
    filmeRepository.save(filmeNoBanco);

    Filme filmeIdentificadorRepetido = new Filme();
    filmeIdentificadorRepetido.setIdPublico(uuidNaoPresenteNoBanco);
    filmeIdentificadorRepetido.setNomeFilme(nomeFilme);

    var requisicaoDeFilmeComIdentificadorNaoPresenteNaLista =
        put("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeIdentificadorRepetido));

    var resultadoRequisicaoDeFilmeComIdentificadorNaoPresenteNaLista =
        mockMvc.perform(requisicaoDeFilmeComIdentificadorNaoPresenteNaLista);

    resultadoRequisicaoDeFilmeComIdentificadorNaoPresenteNaLista.andExpect(status().isBadRequest());
  }
}
