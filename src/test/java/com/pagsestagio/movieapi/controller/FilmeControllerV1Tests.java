package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmeControllerV1Tests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    FilmeRepository filmeRepository;

    Filme filmeSomenteIdentificador = new Filme(null, 1, null,null);
    Filme filmeSomenteNome = new Filme(null, null, null, "Avatar");
    Filme filmeIdentificadorENomeUm = new Filme(null, 1, null, "Avatar");
    Filme filmeIdentificadorENomeDois= new Filme(null,1000, null, "Vingadores");
    Filme filmeRepeticaoIdentificador = new Filme(null, 1, null, "Homem Aranha");


    @BeforeEach
    public void setUp() throws Exception {
        filmeRepository.deleteAll();
    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaResquisicaoPossuiNomeNulo() throws Exception {

        var requiquicaoDeFilmeComNomeNulo = post("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComNomeNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorNulo() throws Exception {

        var requiquicaoDeFilmeComIdentificadorNulo = post("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoFilmeDaRequisicaoPossuiIdentificadorENome() throws Exception {

        var requiquicaoDeFilmeComIdentificadorENome = post("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequisicao = mockMvc.perform(requiquicaoDeFilmeComIdentificadorENome);

        resultadoRequisicao.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorJaExistente() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeComIdentificadorJaExistente = post("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeNoBanco));

        var resultadorequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadorequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificador = get("/v1/filmes/1");

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarUmIdentificadorNaoCadastrado() throws Exception {

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = get("/v1/filmes/1000");

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarDeletarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeAtravesDeIdentificador = delete("/v1/filmes/1");

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificador);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado() throws Exception {

        var requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = delete("/v1/filmes/1000");

        var resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requiquicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequiquicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarBadRequestQuandoRequsicaoTentarAtualizarFilmeSomenteComNomeNulo() throws Exception {

        var requiquicaoDeFilmeComNomeNulo = put("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequiquicaoDeFilmeComNomeNulo = mockMvc.perform(requiquicaoDeFilmeComNomeNulo);

        resultadoRequiquicaoDeFilmeComNomeNulo.andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarFilmeSomenteComIdentificadorNulo() throws Exception {

        var requiquicaoDeFilmeComIdentificadorNulo = put("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequiquicaoDeFilmeComIdentificadorNulo = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNulo);

        resultadoRequiquicaoDeFilmeComIdentificadorNulo.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requiquicaoDeFilmeComIdentificadorJaExistente = put("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeRepeticaoIdentificador));

        var resultadoRequiquicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requiquicaoDeFilmeComIdentificadorJaExistente);

        resultadoRequiquicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes() throws Exception {

        var requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = put("/v1/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeDois));

        var resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista = mockMvc.perform(requiquicaoDeFilmeComIdentificadorNaoPresenteNaLista);

        resultadoRequiquicaoDeFilmeComIdentificadorNaoPresenteNaLista.andExpect(status().isBadRequest());

    }

}