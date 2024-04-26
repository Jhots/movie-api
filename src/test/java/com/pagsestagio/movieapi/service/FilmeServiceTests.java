package com.pagsestagio.movieapi.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import com.pagsestagio.movieapi.model.Filme;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FilmeServiceTests {
    private Map<Integer, String> bancoDeDados;
    private FilmeService service;

    @BeforeEach
    public void inicializar() {
        bancoDeDados = new HashMap<>();
        service = new FilmeService(bancoDeDados);
    }

    @AfterEach
    public void limparBancoDeDados() {
        bancoDeDados.clear();
    }

    @Test
    public void devePegarUmFilmePorIdQuandoOFilmeExiste(){
        bancoDeDados.put(1, "Matrix");

        var resultadoFilmePesquisado = service.pegarFilmePeloId(1);

        assertNotNull(resultadoFilmePesquisado.filme(), "O filme deve ser retornado quando o filme existe.");
        assertNull(resultadoFilmePesquisado.excecao(), "Não deve haver erro quando o filme existe.");
        assertEquals("Matrix", resultadoFilmePesquisado.filme().nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    }

    @Test
    public void deveRetornarNullQuandoFilmeNaoExiste(){
        var resultadoFilmePesquisado = service.pegarFilmePeloId(1);

        assertNull(resultadoFilmePesquisado.filme(), "O filme deve ser nulo quando não existe.");
        assertNotNull(resultadoFilmePesquisado.excecao(), "Deve haver erro quando o filme não existe.");
    }

    @Test
    public void deveCriarNovoFilmeQuandoIdentificadorEhValido(){
        Filme novoFilme = new Filme(1, "Matrix");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNotNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes não deve ser nula após a criação de um novo filme.");
        assertTrue(resultadoFilmeCriado.listaDeFilmes().contains(novoFilme), "A lista de filmes deve conter o novo filme criado.");
        assertNull(resultadoFilmeCriado.excecao(), "Não deve haver erro ao criar um novo filme com identificador válido.");
    }

    @Test
    public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste(){
        bancoDeDados.put(1, "Matrix");
        Filme novoFilme = new Filme(1, "Star Wars");

        var resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNull(resultadoFilmeCriado.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser criado.");
        assertNotNull(resultadoFilmeCriado.excecao(), "Deve haver erro ao tentar criar um filme com identificador já existente.");
    }

    @Test
    public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido(){
        bancoDeDados.put(1, "Matrix");
        Filme filmeAtualizado = new Filme(1, "Matrix Reloaded");

        var resultadoFilmeAtualizado = service.atualizarFilme(filmeAtualizado);

        assertNotNull(resultadoFilmeAtualizado.listaDeFilmes(), "A lista de filmes não deve ser nula após a atualização do filme.");
        assertTrue(resultadoFilmeAtualizado.listaDeFilmes().contains(filmeAtualizado), "A lista de filmes deve conter o filme atualizado.");
        assertNull(resultadoFilmeAtualizado.excecao(), "Não deve haver erro ao atualizar um filme existente.");
    }

    @Test
    public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste(){
        Filme filmeNaoExistente = new Filme(1, "Matrix Reloaded");

        var resultadoFilmeAtualizado = service.atualizarFilme(filmeNaoExistente);

        assertNull(resultadoFilmeAtualizado.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser atualizado.");
        assertNotNull(resultadoFilmeAtualizado.excecao(), "Deve haver erro ao tentar atualizar um filme com identificador inexistente.");
    }

    @Test
    public void deveDeletarFilmeExistenteQuandoIdentificadorEhValido(){
        bancoDeDados.put(1, "Matrix");

        var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(1);

        assertNotNull(resultadoSolicitacaoDeletarFilme.listaDeFilmes(), "A lista de filmes não deve ser nula após a exclusão do filme.");
        assertTrue(resultadoSolicitacaoDeletarFilme.listaDeFilmes().isEmpty(), "A lista de filmes deve estar vazia após a exclusão do filme.");
        assertNull(resultadoSolicitacaoDeletarFilme.excecao(), "Não deve haver erro ao excluir um filme existente.");
    }

    @Test
    public void naoDeveDeletarFilmeQuandoIdentificadorNaoExiste(){
        var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(1);

        assertNull(resultadoSolicitacaoDeletarFilme.listaDeFilmes(), "A lista de filmes deve ser nula se o filme não puder ser excluído.");
        assertNotNull(resultadoSolicitacaoDeletarFilme.excecao(), "Deve haver erro ao tentar excluir um filme com identificador inexistente.");
    }
}
