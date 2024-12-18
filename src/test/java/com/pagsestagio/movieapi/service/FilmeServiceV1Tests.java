package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV1;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoV1;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FilmeServiceV1Tests {
  private FilmeRepository filmeRepository = Mockito.mock(FilmeRepository.class);
  private FilmeOutboxService filmeOutboxService = Mockito.mock(FilmeOutboxService.class);
  private KafkaTemplate<String, String> kafkaTemplate;
  private FilmeService service = new FilmeService(filmeRepository, filmeOutboxService, kafkaTemplate);

  @Test
  public void devePegarUmFilmePorIdQuandoOFilmeExiste() {
    Filme filmeCriado = new Filme();
    filmeCriado.setId(1);
    filmeCriado.setIdLegado(1);
    filmeCriado.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.of(filmeCriado));

    FilmeResultadoRetornaFilmeOuExcecaoV1 resultadoFilmePesquisado = service.pegarFilmePeloIdV1(1);

    assertNotNull(resultadoFilmePesquisado.id(), "O id deve ser retornado quando o filme existe.");
    assertNull(
            resultadoFilmePesquisado.mensagemStatus(), "Não deve haver erro quando o filme existe.");
    assertEquals(
            "Matrix",
            resultadoFilmePesquisado.nomeFilme(),
            "O nome do filme retornado deve ser 'Matrix'.");
  }

  @Test
  public void deveRetornarNullQuandoFilmeNaoExiste() {
    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.empty());

    var resultadoFilmePesquisado = service.pegarFilmePeloIdV1(1);

    assertNull(resultadoFilmePesquisado.id(), "O id do filme deve ser nulo quando não existe.");
    assertNull(
            resultadoFilmePesquisado.nomeFilme(), "O nome do filme deve ser nulo quando não existe.");
    assertNotNull(
            resultadoFilmePesquisado.mensagemStatus(),
            "Deve haver uma mensagem quando o filme não existe.");
  }

  @Test
  public void deveCriarNovoFilmeQuandoIdentificadorEhValido() {
    FilmeDTOV1 novoFilme = new FilmeDTOV1(1, "Matrix");

    Mockito.when(filmeRepository.findByNomeFilme("Matrix")).thenReturn(Optional.empty());
    Mockito.when(filmeRepository.existsByIdLegado(1)).thenReturn(false);

    Filme filmeCriado = new Filme();
    filmeCriado.setIdLegado(1);
    filmeCriado.setNomeFilme("Matrix");
    Mockito.when(filmeRepository.save(filmeCriado)).thenReturn(filmeCriado);
    Mockito.when(filmeRepository.findAllByIdLegadoIsNotNull())
            .thenReturn(Collections.singletonList(filmeCriado));

    var resultadoFilmeCriado = service.criarFilmeV1(novoFilme);

    assertNotNull(
            resultadoFilmeCriado.listaDeFilmes(),
            "A lista de filmes não deve ser nula após a criação de um novo filme.");
    assertEquals(
            1,
            resultadoFilmeCriado.listaDeFilmes().size(),
            "A lista de filmes deve conter exatamente um filme.");
    assertNull(
            resultadoFilmeCriado.mensagemStatus(),
            "Não deve haver erro ao criar um novo filme com identificador válido.");
    assertEquals(
            "Matrix",
            resultadoFilmeCriado.listaDeFilmes().get(0).getNomeFilme(),
            "O nome do filme retornado deve ser 'Matrix'.");
  }

  @Test
  public void naoDeveCriarNovoFilmeQuandoIdentificadorJaExiste() {
    FilmeDTOV1 novoFilme = new FilmeDTOV1(1, "Star Wars");
    Filme filmeExistente = new Filme();
    filmeExistente.setIdLegado(1);
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByNomeFilme("Star Wars"))
            .thenReturn(Optional.of(filmeExistente));

    var resultadoFilmeCriado = service.criarFilmeV1(novoFilme);

    assertNull(
            resultadoFilmeCriado.listaDeFilmes(),
            "A lista de filmes deve ser nula se o filme não puder ser criado.");
    assertNotNull(
            resultadoFilmeCriado.mensagemStatus(),
            "Deve haver erro ao tentar criar um filme com identificador já existente.");
  }

  @Test
  public void deveAtualizarFilmeExistenteQuandoIdentificadorEhValido() {
    Filme filmeExistente = new Filme();
    filmeExistente.setIdLegado(1);
    filmeExistente.setNomeFilme("Matrix");
    FilmeDTOV1 filmeAtualizado = new FilmeDTOV1(1, "Matrix Reloaded");

    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.of(filmeExistente));
    Mockito.when(filmeRepository.save(filmeExistente)).thenReturn(filmeExistente);
    Mockito.when(filmeRepository.findAllByIdLegadoIsNotNull())
            .thenReturn(Collections.singletonList(filmeExistente));

    var resultadoFilmeAtualizado = service.atualizarFilmeV1(filmeAtualizado);

    assertNotNull(
            resultadoFilmeAtualizado.listaDeFilmes(),
            "A lista de filmes não deve ser nula após a atualização do filme.");
    assertEquals(
            "Matrix Reloaded",
            resultadoFilmeAtualizado.listaDeFilmes().get(0).getNomeFilme(),
            "O nome do filme deve ser 'Matrix Reloaded'.");
    assertNull(
            resultadoFilmeAtualizado.mensagemStatus(),
            "Não deve haver erro ao atualizar um filme existente.");
  }

  @Test
  public void naoDeveAtualizarFilmeQuandoIdentificadorNaoExiste() {
    FilmeDTOV1 filmeNaoExistente = new FilmeDTOV1(1, "Matrix Reloaded");

    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.empty());

    var resultadoFilmeAtualizado = service.atualizarFilmeV1(filmeNaoExistente);

    assertNull(
            resultadoFilmeAtualizado.listaDeFilmes(),
            "A lista de filmes deve ser nula se o filme não puder ser atualizado.");
    assertNotNull(
            resultadoFilmeAtualizado.mensagemStatus(),
            "Deve haver erro ao tentar atualizar um filme com identificador inexistente.");
  }

  @Test
  public void deveDeletarFilmeExistenteQuandoIdentificadorEhValido() {
    Filme filmeExistente = new Filme();
    filmeExistente.setIdLegado(1);
    filmeExistente.setNomeFilme("Matrix");

    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.of(filmeExistente));

    var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloIdV1(1);

    assertNotNull(
            resultadoSolicitacaoDeletarFilme.listaDeFilmes(),
            "A lista de filmes não deve ser nula após a exclusão do filme.");
    assertTrue(
            resultadoSolicitacaoDeletarFilme.listaDeFilmes().isEmpty(),
            "A lista de filmes deve estar vazia após a exclusão do filme.");
    assertNull(
            resultadoSolicitacaoDeletarFilme.mensagemStatus(),
            "Não deve haver erro ao excluir um filme existente.");
  }

  @Test
  public void naoDeveDeletarFilmeQuandoIdentificadorNaoExiste() {
    Mockito.when(filmeRepository.findByIdLegado(1)).thenReturn(Optional.empty());

    var resultadoSolicitacaoDeletarFilme = service.deletarFilmePeloIdV1(1);

    assertNull(
            resultadoSolicitacaoDeletarFilme.listaDeFilmes(),
            "A lista de filmes deve ser nula se o filme não puder ser excluído.");
    assertNotNull(
            resultadoSolicitacaoDeletarFilme.mensagemStatus(),
            "Deve haver erro ao tentar excluir um filme com identificador inexistente.");
  }
}