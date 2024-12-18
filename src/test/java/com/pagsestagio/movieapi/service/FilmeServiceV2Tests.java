package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class FilmeServiceV2Tests {

  private FilmeRepository filmeRepository = Mockito.mock(FilmeRepository.class);
  private FilmeOutboxService filmeOutboxService = Mockito.mock(FilmeOutboxService.class);
  private FilmeService service = new FilmeService(filmeRepository, filmeOutboxService);

  @Test
  public void devePegarUmFilmePorIdQuandoOFilmeExiste() {
    UUID idPublico = UUID.randomUUID();
    Filme filme = new Filme();
    filme.setIdPublico(idPublico);
    filme.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.of(filme));

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmePesquisado =
        service.pegarFilmePeloIdV2(idPublico);

    assertNotNull(
        resultadoFilmePesquisado.idpublico(), "O id deve ser retornado quando o filme existe.");
    assertNull(
        resultadoFilmePesquisado.mensagemStatus(), "Não deve haver erro quando o filme existe.");
    assertEquals(
        "Matrix",
        resultadoFilmePesquisado.nomeFilme(),
        "O nome do filme retornado deve ser 'Matrix'.");
  }

  @Test
  public void deveRetornarMensagemQuandoFilmeNaoExiste() {
    UUID idPublico = UUID.randomUUID();

    Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmePesquisado =
        service.pegarFilmePeloIdV2(idPublico);

    assertNull(
        resultadoFilmePesquisado.idpublico(), "O id do filme deve ser nulo quando não existe.");
    assertNull(
        resultadoFilmePesquisado.nomeFilme(), "O nome do filme deve ser nulo quando não existe.");
    assertEquals(
        "Identificador não encontrado! Não foi possível obter o filme.",
        resultadoFilmePesquisado.mensagemStatus(),
        "Deve haver uma mensagem quando o filme não existe.");
  }

  @Test
  public void deveCriarNovoFilmeQuandoIdentificadorEhValido() {
    FilmeDTOV2 novoFilme = new FilmeDTOV2(null, null, null, "Matrix");

    Mockito.when(filmeRepository.findByNomeFilme("Matrix")).thenReturn(Optional.empty());

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilmeV2(novoFilme);

    assertNotNull(
        resultadoFilmeCriado.idpublico(), "O id do filme deve ser retornado após a criação.");
    assertEquals(
        "Matrix", resultadoFilmeCriado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    assertNull(
        resultadoFilmeCriado.mensagemStatus(),
        "Não deve haver erro ao criar um novo filme com identificador válido.");
  }

  @Test
  public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste() {
    FilmeDTOV2 novoFilme = new FilmeDTOV2(null, null, null, "Matrix");
    Filme filmeExistente = new Filme();
    filmeExistente.setIdPublico(UUID.randomUUID());
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByNomeFilme("Matrix")).thenReturn(Optional.of(filmeExistente));

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilmeV2(novoFilme);

    assertNull(
        resultadoFilmeCriado.idpublico(),
        "O id do filme deve ser nulo se o filme não puder ser criado.");
    assertNull(
        resultadoFilmeCriado.nomeFilme(),
        "O nome do filme deve ser nulo se o filme não puder ser criado.");
    assertEquals(
        "Este filme já está cadastrado com o seguinte ID: "
            + filmeExistente.getIdPublico()
            + ". Faça um PUT para atualizar.",
        resultadoFilmeCriado.mensagemStatus(),
        "Deve haver erro ao tentar criar um filme com identificador já existente.");
  }

  @Test
  public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido() {
    UUID idPublico = UUID.randomUUID();
    FilmeDTOV2 filmeAtualizado = new FilmeDTOV2(null, null, idPublico, "Matrix Reloaded");
    Filme filmeExistente = new Filme();
    filmeExistente.setIdPublico(idPublico);
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdPublico(idPublico))
        .thenReturn(Optional.of(filmeExistente));
    Mockito.when(filmeRepository.save(Mockito.any(Filme.class))).thenReturn(filmeExistente);

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeAtualizado =
        service.atualizarFilmeV2(filmeAtualizado);

    assertEquals(
        idPublico,
        resultadoFilmeAtualizado.idpublico(),
        "O id do filme retornado deve ser igual ao id do filme atualizado.");
    assertEquals(
        "Matrix Reloaded",
        resultadoFilmeAtualizado.nomeFilme(),
        "O nome do filme retornado deve ser 'Matrix Reloaded'.");
    assertNull(
        resultadoFilmeAtualizado.mensagemStatus(),
        "Não deve haver erro ao atualizar um filme existente.");
  }

  @Test
  public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste() {
    UUID idPublico = UUID.randomUUID();
    FilmeDTOV2 filmeNaoExistente = new FilmeDTOV2(null, null, idPublico, "Matrix Reloaded");

    Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeAtualizado =
        service.atualizarFilmeV2(filmeNaoExistente);

    assertNull(
        resultadoFilmeAtualizado.idpublico(),
        "O id do filme deve ser nulo se o filme não puder ser atualizado.");
    assertNull(
        resultadoFilmeAtualizado.nomeFilme(),
        "O nome do filme deve ser nulo se o filme não puder ser atualizado.");
    assertEquals(
        "Não há filme cadastrado com o ID: " + idPublico + ". Faça um POST para inserir.",
        resultadoFilmeAtualizado.mensagemStatus(),
        "Deve haver erro ao tentar atualizar um filme com identificador inexistente.");
  }

  @Test
  public void deveDeletarFilmeExistenteQuandoIdentificadorEhValido() {
    UUID idPublico = UUID.randomUUID();
    Filme filmeExistente = new Filme();
    filmeExistente.setIdPublico(idPublico);
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdPublico(idPublico))
        .thenReturn(Optional.of(filmeExistente));

    FilmeResultadoRetornaFilmeOuMensagem resultadoSolicitacaoDeletarFilme =
        service.deletarFilmePeloIdV2(idPublico);

    assertNull(
        resultadoSolicitacaoDeletarFilme.idpublico(),
        "O id do filme deve ser nulo após a exclusão.");
    assertNull(
        resultadoSolicitacaoDeletarFilme.nomeFilme(),
        "O nome do filme deve ser nulo após a exclusão.");
    assertEquals(
        "Filme excluído com sucesso!",
        resultadoSolicitacaoDeletarFilme.mensagemStatus(),
        "Deve haver mensagem de sucesso ao excluir um filme existente.");
  }

  @Test
  public void naoDeveDeletarFilmeQuandoIdentificadorNaoExiste() {
    UUID idPublico = UUID.randomUUID();

    Mockito.when(filmeRepository.findByIdPublico(idPublico)).thenReturn(Optional.empty());

    FilmeResultadoRetornaFilmeOuMensagem resultadoSolicitacaoDeletarFilme =
        service.deletarFilmePeloIdV2(idPublico);

    assertNull(
        resultadoSolicitacaoDeletarFilme.idpublico(),
        "O id do filme deve ser nulo se o filme não puder ser excluído.");
    assertNull(
        resultadoSolicitacaoDeletarFilme.nomeFilme(),
        "O nome do filme deve ser nulo se o filme não puder ser excluído.");
    assertEquals(
        "Identificador não encontrado! Não foi possível excluir o filme.",
        resultadoSolicitacaoDeletarFilme.mensagemStatus(),
        "Deve haver erro ao tentar excluir um filme com identificador inexistente.");
  }

  @Test
  public void deveCriarNovoFilmeQuandoTodosOsCamposSaoValidos() {
    FilmeDTOV2 novoFilme =
        new FilmeDTOV2(
            null,
            null,
            null,
            "Matrix",
            "Sinopse do filme",
            "Ficção Científica",
            1999,
            "Wachowskis");

    Mockito.when(filmeRepository.findByNomeFilme("Matrix")).thenReturn(Optional.empty());

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilmeV2(novoFilme);

    assertNotNull(
        resultadoFilmeCriado.idpublico(), "O id do filme deve ser retornado após a criação.");
    assertEquals(
        "Matrix", resultadoFilmeCriado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    assertEquals(
        "Sinopse do filme",
        resultadoFilmeCriado.sinopseFilme(),
        "A sinopse do filme retornado deve ser 'Sinopse do filme'.");
    assertEquals(
        "Ficção Científica",
        resultadoFilmeCriado.categoriaFilme(),
        "A categoria do filme retornado deve ser 'Ficção Científica'.");
    assertEquals(1999, resultadoFilmeCriado.anoFilme(), "O ano do filme retornado deve ser 1999.");
    assertEquals(
        "Wachowskis",
        resultadoFilmeCriado.diretorFilme(),
        "O diretor do filme retornado deve ser 'Wachowskis'.");
    assertNull(
        resultadoFilmeCriado.mensagemStatus(),
        "Não deve haver erro ao criar um novo filme com identificador válido.");
  }

  @Test
  public void deveAtualizarFilmeExistenteQuandoTodosOsCamposSaoValidos() {
    UUID idPublico = UUID.randomUUID();
    FilmeDTOV2 filmeAtualizado =
        new FilmeDTOV2(
            null, null, idPublico, "Matrix Reloaded", "Nova sinopse", "Ação", 2003, "Wachowskis");
    Filme filmeExistente = new Filme();
    filmeExistente.setIdPublico(idPublico);
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdPublico(idPublico))
        .thenReturn(Optional.of(filmeExistente));
    Mockito.when(filmeRepository.save(Mockito.any(Filme.class))).thenReturn(filmeExistente);

    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeAtualizado =
        service.atualizarFilmeV2(filmeAtualizado);

    assertEquals(
        idPublico,
        resultadoFilmeAtualizado.idpublico(),
        "O id do filme retornado deve ser igual ao id do filme atualizado.");
    assertEquals(
        "Matrix Reloaded",
        resultadoFilmeAtualizado.nomeFilme(),
        "O nome do filme retornado deve ser 'Matrix Reloaded'.");
    assertEquals(
        "Nova sinopse",
        resultadoFilmeAtualizado.sinopseFilme(),
        "A sinopse do filme retornado deve ser 'Nova sinopse'.");
    assertEquals(
        "Ação",
        resultadoFilmeAtualizado.categoriaFilme(),
        "A categoria do filme retornado deve ser 'Ação'.");
    assertEquals(
        2003, resultadoFilmeAtualizado.anoFilme(), "O ano do filme retornado deve ser 2003.");
    assertEquals(
        "Wachowskis",
        resultadoFilmeAtualizado.diretorFilme(),
        "O diretor do filme retornado deve ser 'Wachowskis'.");
    assertNull(
        resultadoFilmeAtualizado.mensagemStatus(),
        "Não deve haver erro ao atualizar um filme existente.");
  }

  @Test
  public void deveChamarOutboxQuandoFilmeEhCriado() {
    FilmeDTOV2 novoFilme = new FilmeDTOV2(null, null, null, "Matrix");

    Mockito.when(filmeRepository.findByNomeFilme("Matrix")).thenReturn(Optional.empty());
    FilmeResultadoRetornaFilmeOuMensagem resultadoFilmeCriado = service.criarFilmeV2(novoFilme);
    Mockito.verify(filmeRepository).save(Mockito.any(Filme.class));
    Mockito.verify(filmeOutboxService).salvaEventoNaTabelaDeOutbox(Mockito.any(Filme.class));

    assertNotNull(
            resultadoFilmeCriado.idpublico(), "O id do filme deve ser retornado após a criação.");
    assertEquals(
            "Matrix", resultadoFilmeCriado.nomeFilme(), "O nome do filme retornado deve ser 'Matrix'.");
    assertNull(
            resultadoFilmeCriado.mensagemStatus(),
            "Não deve haver erro ao criar um novo filme com identificador válido.");
  }
}
