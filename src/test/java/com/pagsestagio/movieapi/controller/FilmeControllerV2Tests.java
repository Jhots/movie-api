package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmeControllerV2Tests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    FilmeRepository filmeRepository;

    UUID uuidFilme = UUID.fromString("8bec4f7d-0dc5-4a93-a704-ccb44cc6fa2f");
    String nomeFilme = "Avatar";
    UUID uuidNaoPresenteNoBanco = UUID.fromString("f1ea8127-3766-470d-900a-0dc692fb1741");


    FilmeDTO filmeSomenteNome = new FilmeDTO(null, null,null, "Avatar");
    FilmeDTO filmeSemNomeEIdentificador = new FilmeDTO(null, null, null, null);
    FilmeDTO filmeIdentificadorENomeUm = new FilmeDTO(null, null, uuidFilme, nomeFilme);



    @BeforeEach
    public void setUp() throws Exception {
        filmeRepository.deleteAll();
    }



    @Test
    void deveRetornarOkQuandoFilmeDaResquisicaoPossuiSomenteNome() throws Exception {

        var requiquicaoDeFilmeSomenteNome = post("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeSomenteNome);

        resultadoRequisicao.andExpect(status().isOk());

    }



    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiNomeNulo() throws Exception {

        var requisicaoDeFilmeComNomeNulo = post("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSemNomeEIdentificador));

        var resultadoRequisicao = mockMvc.perform(requisicaoDeFilmeComNomeNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }



    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoJaExiste() throws Exception {

        Filme filme = new Filme();
        filme.setIdPublico(uuidFilme);
        filme.setNomeFilme(nomeFilme);
        filmeRepository.save(filme);

        var requiquicaoDeFilmeComIdentificadorJaExistente = post("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadorequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadorequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isBadRequest());

    }



    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificador = get("/v2/filmes/{idPublico}", filmeNoBanco.getIdPublico());

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }



    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarUmIdentificadorNaoCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = get("/v2/filmes/{uuidNaoPresenteNoBanco}", uuidNaoPresenteNoBanco);

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }



    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarDeletarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificador = delete("/v2/filmes/{idPublico}", filmeNoBanco.getIdPublico());

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }



    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = delete("/v2/filmes/{uuidNaoPresenteNoBanco}", uuidNaoPresenteNoBanco);

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }



    @Test
    void deveRetornarBadRequestQuandoRequsicaoTentarAtualizarFilmeSomenteComNomeNulo() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeComNomeNulo = put("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeNoBanco));

        var resultadoRequiquicaoDeFilmeComNomeNulo = mockMvc.perform(requiquicaoDeFilmeComNomeNulo);

        resultadoRequiquicaoDeFilmeComNomeNulo.andExpect(status().isBadRequest());
    }



    @Test
    void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        FilmeDTO filmeAtualizado = new FilmeDTO(null, null, filmeNoBanco.getIdPublico(), nomeFilme);

        var requiquicaoDeFilmeComIdentificadorJaExistente = put("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeAtualizado));

        var resultadoRequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isOk());

    }



    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdPublico(uuidFilme);
        filmeNoBanco.setNomeFilme(nomeFilme);
        filmeRepository.save(filmeNoBanco);

        Filme filmeIdentificadorRepetido = new Filme();
        filmeIdentificadorRepetido.setIdPublico(uuidNaoPresenteNoBanco);
        filmeIdentificadorRepetido.setNomeFilme(nomeFilme);

        var requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = put("/v2/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorRepetido));

        var resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista);

        resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista.andExpect(status().isBadRequest());

    }

}