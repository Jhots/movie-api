package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FilmeControllerV2Tests extends IntegrationBaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired FilmeRepository filmeRepository;

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

  FilmeDTOV2 filmeTotalmenteIncompleto =
      new FilmeDTOV2(null, null, null, null, null, null, null, null);
  FilmeDTOV2 filmeSomenteNome = new FilmeDTOV2(null, null, null, "Avatar");
  FilmeDTOV2 filmeSemNomeEIdentificador = new FilmeDTOV2(null, null, null, null);
  FilmeDTOV2 filmeIdentificadorENomeUm = new FilmeDTOV2(null, null, uuidFilme, nomeFilme);

  @BeforeEach
  public void setUp() throws Exception {
    filmeRepository.deleteAll();
  }

  @Test
  void deveRetornarOkQuandoFilmeDaResquisicaoPossuiSomenteNome() throws Exception {

    var requisicaoDeFilmeSomenteNome =
        post("/v2/filmes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeSomenteNome));

    var resultadoRequisicao = mockMvc.perform(requisicaoDeFilmeSomenteNome);

    resultadoRequisicao.andExpect(status().isOk());
  }

  @Test
  void deveRetornarOkQuandoFilmeDaResquisicaoECompleto() throws Exception {

    var requisicaoDeFilmeCompleto =
            post("/v2/filmes")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(filmeCompleto));

    var resultadoRequisicaoDeFilmeCompleto = mockMvc.perform(requisicaoDeFilmeCompleto);

    resultadoRequisicaoDeFilmeCompleto.andExpect(status().isOk());
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

    var resultadoRequisicaoFilmeComplexoExcetoNome = mockMvc.perform(requisicaoDeFilmeComplexoExcetoNome);


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
  void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado() throws Exception {

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
  void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarFilmeSomenteComNomeNulo() throws Exception {

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
  void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme() throws Exception {

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
  void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes() throws Exception {

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