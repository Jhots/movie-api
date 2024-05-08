package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    UUID idPublicoaleatorio = UUID.randomUUID();


    FilmeDTO filmeSomenteIdentificador = new FilmeDTO(idPublicoaleatorio,null);
    FilmeDTO filmeSomenteNome = new FilmeDTO(null, "Avatar");
    FilmeDTO filmeSemNomeEIdentificador = new FilmeDTO(null, null);
    FilmeDTO filmeIdentificadorENomeUm = new FilmeDTO(idPublicoaleatorio, "Avatar");


    @BeforeEach
    public void setUp() throws Exception {

    }


    @Test
    void deveRetornarBadRequestQuandoFilmeDaResquisicaoPossuiNomeNulo() throws Exception {

        var requiquicaoDeFilmeComNomeNulo = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComNomeNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorNulo() throws Exception {

        var requiquicaoDeFilmeComIdentificadorNulo = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoFilmeDaRequisicaoPossuiIdentificadorENome() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);

        resultadoRequisicao.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorJaExistente() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeComIdentificadorJaExistente = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadorequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadorequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarUmIdentificadorCadastrado() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeAtravesDeIdentificador = get("/filmes/{}");


        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarUmIdentificadorNaoCadastrado() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = get("/filmes/1000");

        var resultadoRequiquicaoDeFilmeComIdentificadorENome= mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarDeletarUmIdentificadorCadastrado() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeAtravesDeIdentificador = delete("/filmes/1");

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = delete("/filmes/{id}");

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarBadRequestQuandoRequsicaoTentarAtualizarFilmeSomenteComNomeNulo() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeComNomeNulo = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeComNomeNulo = mockMvc.perform(requiquicaoDeFilmeComNomeNulo);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeComNomeNulo.andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarFilmeSomenteComIdentificadorNulo() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeComIdentificadorNulo = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeComIdentificadorNulo = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNulo);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeComIdentificadorNulo.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requiquicaoDeFilmeComIdentificadorJaExistente = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));


        var requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequiquicaoDeFilmeComIdentificadorENome = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);
        var resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista);

        resultadoRequiquicaoDeFilmeComIdentificadorENome.andExpect(status().isOk());
        resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista.andExpect(status().isBadRequest());

    }

}