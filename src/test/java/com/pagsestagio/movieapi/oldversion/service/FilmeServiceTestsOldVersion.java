package com.pagsestagio.movieapi.oldversion.service;


import com.pagsestagio.movieapi.oldversion.model.FilmeDTOOldVersion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FilmeServiceTestsOldVersion {
    private Map<Integer, String> bancoDeDados;
    private FilmeServiceOldVersion service;

    @BeforeEach
    public void inicializar() {
        bancoDeDados = new HashMap<>();
        service = new FilmeServiceOldVersion(bancoDeDados);
    }

    @Test
    public void devePegarUmFilmePorIdQuandoOFilmeExiste(){
        bancoDeDados.put(1, "Matrix");

        var resultadoFilmePesquisado = service.pegarFilmePeloId(1);

        assertNotNull(resultadoFilmePesquisado.id(), "O id deve ser retornado quando o filme existe.");
        assertNull(resultadoFilmePesquisado.mensagemStatus(), "Não deve haver erro quando o filme existe.");
        assertEquals("Matrix", resultadoFilmePesquisado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    }

    @Test
    public void deveRetornarNullQuandoFilmeNaoExiste(){
        var resultadoFilmePesquisado = service.pegarFilmePeloId(1);

        assertNull(resultadoFilmePesquisado.id(), "O id do filme deve ser nulo quando não existe.");
        assertNull(resultadoFilmePesquisado.nomeFilme(), "O nome do filme deve ser nulo quando não existe.");
        assertNotNull(resultadoFilmePesquisado.mensagemStatus(), "Deve haver uma mensagem quando o filme não existe.");
    }

    @Test
    public void deveCriarNovoFilmeQuandoIdentificadorEhValido(){
        FilmeDTOOldVersion novoFilme = new FilmeDTOOldVersion(1, "Matrix");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNotNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes não deve ser nula após a criação de um novo filme.");
        assertTrue(resultadoFilmeCriado.listaDeFilmes().contains(novoFilme), "A lista de filmes deve conter o novo filme criado.");
        assertNull(resultadoFilmeCriado.mensagemStatus(), "Não deve haver erro ao criar um novo filme com identificador válido.");
    }

    @Test
    public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste(){
        bancoDeDados.put(1, "Matrix");
        FilmeDTOOldVersion novoFilme = new FilmeDTOOldVersion(1, "Star Wars");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser criado.");
        assertNotNull(resultadoFilmeCriado.mensagemStatus(), "Deve haver erro ao tentar criar um filme com identificador já existente.");
    }

    @Test
    public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido(){
        bancoDeDados.put(1, "Matrix");
        FilmeDTOOldVersion filmeAtualizado = new FilmeDTOOldVersion(1, "Matrix Reloaded");

        var resultadoFilmeAtualizado = service.atualizarFilme(filmeAtualizado);

        assertNotNull(resultadoFilmeAtualizado.listaDeFilmes(), "A lista de filmes não deve ser nula após a atualização do filme.");
        assertTrue(resultadoFilmeAtualizado.listaDeFilmes().contains(filmeAtualizado), "A lista de filmes deve conter o filme atualizado.");
        assertNull(resultadoFilmeAtualizado.mensagemStatus(), "Não deve haver erro ao atualizar um filme existente.");
    }

    @Test
    public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste(){
        FilmeDTOOldVersion filmeNaoExistente = new FilmeDTOOldVersion(1, "Matrix Reloaded");

        var resultadoFilmeAtualizado = service.atualizarFilme(filmeNaoExistente);

        assertNull(resultadoFilmeAtualizado.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser atualizado.");
        assertNotNull(resultadoFilmeAtualizado.mensagemStatus(), "Deve haver erro ao tentar atualizar um filme com identificador inexistente.");
    }

    @Test
    public void deveDeletarFilmeExistenteQuandoIdentificadorEhValido(){
        bancoDeDados.put(1, "Matrix");

        var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(1);

        assertNotNull(resultadoSolicitacaoDeletarFilme.listaDeFilmes(), "A lista de filmes não deve ser nula após a exclusão do filme.");
        assertTrue(resultadoSolicitacaoDeletarFilme.listaDeFilmes().isEmpty(), "A lista de filmes deve estar vazia após a exclusão do filme.");
        assertNull(resultadoSolicitacaoDeletarFilme.mensagemStatus(), "Não deve haver erro ao excluir um filme existente.");
    }

    @Test
    public void naoDeveDeletarFilmeQuandoIdentificadorNaoExiste(){
        var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(1);

        assertNull(resultadoSolicitacaoDeletarFilme.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser excluído.");
        assertNotNull(resultadoSolicitacaoDeletarFilme.mensagemStatus(), "Deve haver erro ao tentar excluir um filme com identificador inexistente.");
    }
}