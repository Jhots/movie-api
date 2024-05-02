package com.pagsestagio.movieapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import com.pagsestagio.movieapi.model.FilmeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FilmeServiceTests {
    /*Adaptar tudo que for relativo ao banco de dados antigo(map)

    private Map<Integer, String> bancoDeDados;
    private FilmeService service;

    @BeforeEach
    public void inicializar() {
        bancoDeDados = new HashMap<>();
        service = new FilmeService(bancoDeDados);
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
        FilmeDTO novoFilme = new FilmeDTO(1, "Matrix");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNotNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes não deve ser nula após a criação de um novo filme.");
        assertTrue(resultadoFilmeCriado.listaDeFilmes().contains(novoFilme), "A lista de filmes deve conter o novo filme criado.");
        assertNull(resultadoFilmeCriado.mensagemStatus(), "Não deve haver erro ao criar um novo filme com identificador válido.");
    }

    @Test
    public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste(){
        bancoDeDados.put(1, "Matrix");
        FilmeDTO novoFilme = new FilmeDTO(1, "Star Wars");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser criado.");
        assertNotNull(resultadoFilmeCriado.mensagemStatus(), "Deve haver erro ao tentar criar um filme com identificador já existente.");
    }

    @Test
    public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido(){
        bancoDeDados.put(1, "Matrix");
        FilmeDTO filmeAtualizado = new FilmeDTO(1, "Matrix Reloaded");

        var resultadoFilmeAtualizado = service.atualizarFilme(filmeAtualizado);

        assertNotNull(resultadoFilmeAtualizado.listaDeFilmes(), "A lista de filmes não deve ser nula após a atualização do filme.");
        assertTrue(resultadoFilmeAtualizado.listaDeFilmes().contains(filmeAtualizado), "A lista de filmes deve conter o filme atualizado.");
        assertNull(resultadoFilmeAtualizado.mensagemStatus(), "Não deve haver erro ao atualizar um filme existente.");
    }

    @Test
    public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste(){
        FilmeDTO filmeNaoExistente = new FilmeDTO(1, "Matrix Reloaded");

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
    */
}
