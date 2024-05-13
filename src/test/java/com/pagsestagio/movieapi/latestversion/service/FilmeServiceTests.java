package com.pagsestagio.movieapi.latestversion.service;

import static org.junit.jupiter.api.Assertions.*;

import com.pagsestagio.movieapi.latestversion.model.Filme;
import com.pagsestagio.movieapi.latestversion.model.FilmeDTO;
import com.pagsestagio.movieapi.latestversion.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.latestversion.repository.FilmeRepository;
import com.pagsestagio.movieapi.latestversion.service.FilmeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class FilmeServiceTests {

    private FilmeRepository filmeRepository = Mockito.mock(FilmeRepository.class);
    private FilmeService service = new FilmeService(filmeRepository);


    @Test
    public void devePegarUmFilmePorIdQuandoOFilmeExiste(){
        UUID idPublico = UUID.randomUUID();
        Filme filme = new Filme();
        filme.setIdPublico(idPublico);
        filme.setNome("Matrix");

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.of(filme));

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmePesquisado = service.pegarFilmePeloId(idPublico);

        assertNotNull(resultadoFilmePesquisado.idpublico(), "O id deve ser retornado quando o filme existe.");
        assertNull(resultadoFilmePesquisado.mensagemStatus(), "Não deve haver erro quando o filme existe.");
        assertEquals("Matrix", resultadoFilmePesquisado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    }

    @Test
    public void deveRetornarMensagemQuandoFilmeNaoExiste(){
        UUID idPublico = UUID.randomUUID();

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmePesquisado = service.pegarFilmePeloId(idPublico);

        assertNull(resultadoFilmePesquisado.idpublico(), "O id do filme deve ser nulo quando não existe.");
        assertNull(resultadoFilmePesquisado.nomeFilme(), "O nome do filme deve ser nulo quando não existe.");
        assertEquals("Identificador não encontrado! Não foi possível obter o filme.", resultadoFilmePesquisado.mensagemStatus(), "Deve haver uma mensagem quando o filme não existe.");
    }

    @Test
    public void deveCriarNovoFilmeQuandoIdentificadorEhValido(){
        FilmeDTO novoFilme = new FilmeDTO(null, "Matrix");

        Mockito.when(filmeRepository.findByNome("Matrix")).thenReturn(Optional.empty());

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNotNull(resultadoFilmeCriado.idpublico(), "O id do filme deve ser retornado após a criação.");
        assertEquals("Matrix", resultadoFilmeCriado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
        assertNull(resultadoFilmeCriado.mensagemStatus(), "Não deve haver erro ao criar um novo filme com identificador válido.");
    }

    @Test
    public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste(){
        FilmeDTO novoFilme = new FilmeDTO(null, "Matrix");
        Filme filmeExistente = new Filme();
        filmeExistente.setIdPublico(UUID.randomUUID());
        filmeExistente.setNome("Matrix");

        Mockito.when(filmeRepository.findByNome("Matrix")).thenReturn(Optional.of(filmeExistente));

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilme(novoFilme);

        assertNull(resultadoFilmeCriado.idpublico(), "O id do filme deve ser nulo se o filme não puder ser criado.");
        assertNull(resultadoFilmeCriado.nomeFilme(), "O nome do filme deve ser nulo se o filme não puder ser criado.");
        assertEquals("O registro já existe, faça um PUT para atualizar.", resultadoFilmeCriado.mensagemStatus(), "Deve haver erro ao tentar criar um filme com identificador já existente.");
    }

    @Test
    public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido(){
        UUID idPublico = UUID.randomUUID();
        FilmeDTO filmeAtualizado = new FilmeDTO(idPublico, "Matrix Reloaded");
        Filme filmeExistente = new Filme();
        filmeExistente.setIdPublico(idPublico);
        filmeExistente.setNome("Matrix");

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.of(filmeExistente));
        Mockito.when(filmeRepository.save(Mockito.any(Filme.class))).thenReturn(filmeExistente);

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeAtualizado = service.atualizarFilme(filmeAtualizado);

        assertEquals(idPublico, resultadoFilmeAtualizado.idpublico(), "O id do filme retornado deve ser igual ao id do filme atualizado.");
        assertEquals("Matrix Reloaded", resultadoFilmeAtualizado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix Reloaded'.");
        assertNull(resultadoFilmeAtualizado.mensagemStatus(), "Não deve haver erro ao atualizar um filme existente.");
    }

    @Test
    public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste(){
        UUID idPublico = UUID.randomUUID();
        FilmeDTO filmeNaoExistente = new FilmeDTO(idPublico, "Matrix Reloaded");

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

        FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeAtualizado = service.atualizarFilme(filmeNaoExistente);

        assertNull(resultadoFilmeAtualizado.idpublico(), "O id do filme deve ser nulo se o filme não puder ser atualizado.");
        assertNull(resultadoFilmeAtualizado.nomeFilme(), "O nome do filme deve ser nulo se o filme não puder ser atualizado.");
        assertEquals("O registro não existe, faça um POST para inserir.", resultadoFilmeAtualizado.mensagemStatus(), "Deve haver erro ao tentar atualizar um filme com identificador inexistente.");
    }

    @Test
    public void deveDeletarFilmeExistenteQuandoIdentificadorEhValido(){
        UUID idPublico = UUID.randomUUID();
        Filme filmeExistente = new Filme();
        filmeExistente.setIdPublico(idPublico);
        filmeExistente.setNome("Matrix");

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.of(filmeExistente));

        FilmeResultadoRetornaFilmeOuMensagem resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(idPublico);

        assertNull(resultadoSolicitacaoDeletarFilme.idpublico(), "O id do filme deve ser nulo após a exclusão.");
        assertNull(resultadoSolicitacaoDeletarFilme.nomeFilme(), "O nome do filme deve ser nulo após a exclusão.");
        assertEquals("Filme excluído com sucesso!", resultadoSolicitacaoDeletarFilme.mensagemStatus(), "Deve haver mensagem de sucesso ao excluir um filme existente.");
    }

    @Test
    public void naoDeveDeletarFilmeQuandoIdentificadorNaoExiste(){
        UUID idPublico = UUID.randomUUID();

        Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

        FilmeResultadoRetornaFilmeOuMensagem resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloId(idPublico);

        assertNull(resultadoSolicitacaoDeletarFilme.idpublico(), "O id do filme deve ser nulo se o filme não puder ser excluído.");
        assertNull(resultadoSolicitacaoDeletarFilme.nomeFilme(), "O nome do filme deve ser nulo se o filme não puder ser excluído.");
        assertEquals("Identificador não encontrado! Não foi possível excluir o filme.", resultadoSolicitacaoDeletarFilme.mensagemStatus(), "Deve haver erro ao tentar excluir um filme com identificador inexistente.");
    }
}
