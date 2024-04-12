package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.model.Filme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    @Autowired
    private FilmeController controller;


    Filme filmeSomenteIdentificador = new Filme(1, null);
    Filme filmeSomenteNome = new Filme(null, "Avatar");
    Filme filmeIdentificadorENomeUm = new Filme(1, "Avatar");
    Filme filmeIdentificadorENomeDois= new Filme(1000, "Vingadores");
    Filme filmeRepeticaoIdentificador = new Filme(1, "Homem Aranha");



    @Test
    void SomenteIdentificador() throws Exception {

        //Requisição
        var request = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        // Resultado
        var result = mockMvc.perform(request);

        // Retorno esperado
        result.andExpect(status().isBadRequest());
    }

    @Test
    void SomenteNomeFilme() throws Exception {

        //Requisição
        var request = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        // Resultado
        var result = mockMvc.perform(request);

        // Retorno esperado
        result.andExpect(status().isBadRequest());
    }

    @Test
    void IdentificadorENomeFilme() throws Exception {

        //Requisição
        var request = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        // Resultado
        var result = mockMvc.perform(request);

        // Retorno esperado
        result.andExpect(status().isOk());
    }

    @Test
    void IdentificadorExistenteENomeFilme() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeRepeticaoIdentificador));

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);


        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isBadRequest());

    }

    @Test
    void SolicitarIdentificadorCadastrado() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = get("/filmes/1");

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);


        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isOk());
    }

    @Test
    void SolicitarIdentificadorNãoCadastrado() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = get("/filmes/1000");

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);


        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isNotFound());
    }

    @Test
    void DeletarIdentificadorCadastrado() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = delete("/filmes/1");

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isOk());
    }

    @Test
    void DeletarIdentificadorNãoCadastrado() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = delete("/filmes/1000");

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isNotFound());
    }

    @Test
    void AtualizarSomenteComIdentificador() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteIdentificador));

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isBadRequest());
    }

    @Test
    void AtualizarSomenteComNomeFilme() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeSomenteNome));

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isBadRequest());
    }

    @Test
    void AtualizarComIdentificadorExistenteENomeFilme() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeRepeticaoIdentificador));

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isOk());
    }

    @Test
    void AtualizarComIdentificadorInexistenteENomeFilme() throws Exception {

        //Requisições
        var requestUm = post("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeUm));

        var requestDois = put("/filmes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmeIdentificadorENomeDois));

        // Resultados
        var resultUm = mockMvc.perform(requestUm);
        var resultDois = mockMvc.perform(requestDois);

        // Retornos esperados
        resultUm.andExpect(status().isOk());
        resultDois.andExpect(status().isBadRequest());
    }

}


