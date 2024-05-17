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

        var requisicaoDeFilmeComNomeNulo = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequisicao = mockMvc.perform(requisicaoDeFilmeComNomeNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorNulo() throws Exception {

        var requisicaoDeFilmeComIdentificadorNulo = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequisicao = mockMvc.perform(requisicaoDeFilmeComIdentificadorNulo);

        resultadoRequisicao.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoFilmeDaRequisicaoPossuiIdentificadorENome() throws Exception {

        var requisicaoDeFilmeComIdentificadorENome = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var resultadoRequisicao = mockMvc.perform(requisicaoDeFilmeComIdentificadorENome);

        resultadoRequisicao.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoFilmeDaRequisicaoPossuiIdentificadorJaExistente() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requisicaoDeFilmeComIdentificadorJaExistente = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeNoBanco));

        var resultadorequisicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requisicaoDeFilmeComIdentificadorJaExistente);

        resultadorequisicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requisicaoDeFilmeAtravesDeIdentificador = get("/filmes/1");

        var resultadoRequisicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificador);

        resultadoRequisicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarUmIdentificadorNaoCadastrado() throws Exception {

        var requisicaoDeFilmeAtravesDeIdentificadorNaoExistente = get("/v1/filmes/1000");

        var resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoSolicitarDeletarUmIdentificadorCadastrado() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requisicaoDeFilmeAtravesDeIdentificador = delete("/filmes/1");

        var resultadoRequisicaoDeFilmeAtravesDeIdentificador = mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificador);

        resultadoRequisicaoDeFilmeAtravesDeIdentificador.andExpect(status().isOk());

    }

    @Test
    void deveRetornarNotFoundQuandoRequisicaoSolicitarDeletarUmIdentificadorNaoCadastrado() throws Exception {

        var requisicaoDeFilmeAtravesDeIdentificadorNaoExistente = delete("/filmes/1000");

        var resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente = mockMvc.perform(requisicaoDeFilmeAtravesDeIdentificadorNaoExistente);

        resultadoRequisicaoDeFilmeAtravesDeIdentificadorNaoExistente.andExpect(status().isNotFound());

    }

    @Test
    void deveRetornarBadRequestQuandoRequsicaoTentarAtualizarFilmeSomenteComNomeNulo() throws Exception {

        var requisicaoDeFilmeComNomeNulo = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        var resultadoRequisicaoDeFilmeComNomeNulo = mockMvc.perform(requisicaoDeFilmeComNomeNulo);

        resultadoRequisicaoDeFilmeComNomeNulo.andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarFilmeSomenteComIdentificadorNulo() throws Exception {

        var requisicaoDeFilmeComIdentificadorNulo = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        var resultadoRequisicaoDeFilmeComIdentificadorNulo = mockMvc.perform(requisicaoDeFilmeComIdentificadorNulo);

        resultadoRequisicaoDeFilmeComIdentificadorNulo.andExpect(status().isBadRequest());

    }

    @Test
    void deveRetornarOkQuandoRequisicaoTentarAtualizarFilmeComUmIdentificadorExistenteENomeFilme() throws Exception {

        Filme filmeNoBanco = new Filme();
        filmeNoBanco.setIdLegado(1);
        filmeNoBanco.setNomeFilme("Avatar");
        filmeRepository.save(filmeNoBanco);

        var requisicaoDeFilmeComIdentificadorJaExistente = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeRepeticaoIdentificador));

        var resultadoRequisicaoDeFilmeComIdentificadorJaExistente = mockMvc.perform(requisicaoDeFilmeComIdentificadorJaExistente);

        resultadoRequisicaoDeFilmeComIdentificadorJaExistente.andExpect(status().isOk());

    }

    @Test
    void deveRetornarBadRequestQuandoRequisicaoTentarAtualizarComIdentificadorNaoPresenteNaListaDeFilmes() throws Exception {

        var requisicaoDeFilmeComIdentificadorNaoPresenteNaLista = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeDois));

        var resultadoRequisicaoDeFilmeComIdentificadorNaoPresenteNaLista = mockMvc.perform(requisicaoDeFilmeComIdentificadorNaoPresenteNaLista);

        resultadoRequisicaoDeFilmeComIdentificadorNaoPresenteNaLista.andExpect(status().isBadRequest());

    }

}