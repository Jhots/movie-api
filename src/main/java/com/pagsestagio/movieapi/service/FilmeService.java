package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.apiExterna.omdb.service.OmdbService;
import com.pagsestagio.movieapi.apiExterna.resposta.FilmeRespostaApiExternaRetornaDadosFilme;
import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV1;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoV1;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecaoV1;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

  private final FilmeRepository filmeRepository;

  @Autowired
  public FilmeService(FilmeRepository filmeRepository, OmdbService omdbService) {
    this.filmeRepository = filmeRepository;
    this.omdbService = omdbService;
  }

  private OmdbService omdbService;

  List<FilmeDTOV1> getListaDeFilmesVersaoUm() {
    List<Filme> filmesComIdLegado = filmeRepository.findAllByIdLegadoIsNotNull();
    List<FilmeDTOV1> listaDeFilmes = new ArrayList<>();

    for (Filme filme : filmesComIdLegado) {
      FilmeDTOV1 filmeLegadoDTOV1 = new FilmeDTOV1(filme.getIdLegado(), filme.getNomeFilme());
      listaDeFilmes.add(filmeLegadoDTOV1);
    }

    return listaDeFilmes;
  }

  @Deprecated
  public FilmeResultadoRetornaFilmeOuExcecaoV1 pegarFilmePeloIdV1(Integer idRequisicao) {
    FilmeResultadoRetornaFilmeOuExcecaoV1 retornoDoFilmePorIdentificador = null;

    Optional<Filme> optionalFilme = filmeRepository.findByIdLegado(idRequisicao);

    if (optionalFilme.isPresent()) {
      String nomeDoFilmeEncontrado = optionalFilme.get().getNomeFilme();
      retornoDoFilmePorIdentificador =
          new FilmeResultadoRetornaFilmeOuExcecaoV1(
              optionalFilme.get().getId(), nomeDoFilmeEncontrado, null);
    } else {
      retornoDoFilmePorIdentificador =
          new FilmeResultadoRetornaFilmeOuExcecaoV1(
              null, null, "Identificador não encontrado! Não foi possível obter o filme.");
    }

    return retornoDoFilmePorIdentificador;
  }

  public FilmeResultadoRetornaFilmeOuMensagem pegarFilmePeloIdV2(UUID idRequisicao) {
    FilmeResultadoRetornaFilmeOuMensagem retornoDoFilmePorIdentificador = null;
    Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

    if (optionalFilme.isPresent()) {
      Filme filmeprocurado = optionalFilme.get();
      retornoDoFilmePorIdentificador =
          new FilmeResultadoRetornaFilmeOuMensagem(
              filmeprocurado.getIdPublico(),
              filmeprocurado.getNomeFilme(),
              filmeprocurado.getSinopseFilme(),
              filmeprocurado.getCategoriaFilme(),
              filmeprocurado.getAnoFilme(),
              filmeprocurado.getDiretorFilme());
    } else {
      retornoDoFilmePorIdentificador =
          new FilmeResultadoRetornaFilmeOuMensagem(
              "Identificador não encontrado! Não foi possível obter o filme.");
    }

    return retornoDoFilmePorIdentificador;
  }

  @Deprecated
  @Transactional
  public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 criarFilmeV1(FilmeDTOV1 filmeRequisicao) {
    FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoCriacaoDeFilme = null;

    Optional<Filme> filmeExistente =
        filmeRepository.findByNomeFilme(filmeRequisicao.getNomeFilme());

    if (filmeRequisicao.getIdLegado() == null || filmeRequisicao.getNomeFilme() == null) {
      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
              null, "O identificador e o nome do filme devem ser inseridos.");
    } else if (filmeExistente.isPresent()
        || filmeRepository.existsByIdLegado(filmeRequisicao.getIdLegado())) {
      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
              null, "O registro já existe, faça um PUT para atualizar.");
    } else {
      Filme filmecriado = new Filme();
      filmecriado.setIdLegado(filmeRequisicao.getIdLegado());
      filmecriado.setNomeFilme(filmeRequisicao.getNomeFilme());
      filmeRepository.save(filmecriado);

      List<FilmeDTOV1> listaDeFilmes = getListaDeFilmesVersaoUm();

      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
    }

    return retornoCriacaoDeFilme;
  }

  @Transactional
  public FilmeResultadoRetornaFilmeOuMensagem criarFilmeV2(FilmeDTOV2 filmeRequisicao) {
    FilmeResultadoRetornaFilmeOuMensagem retornoCriacaoDeFilme = null;

    Optional<Filme> filmeExistente =
        filmeRepository.findByNomeFilme(filmeRequisicao.getNomeFilme());

    if (filmeRequisicao.getNomeFilme() == null) {
      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaFilmeOuMensagem("O nome do filme é obrigatório.");
    } else if (filmeExistente.isPresent()) {
      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaFilmeOuMensagem(
              "Este filme já está cadastrado com o seguinte ID: "
                  + filmeExistente.get().getIdPublico()
                  + ". Faça um PUT para atualizar.");
    } else {

      FilmeRespostaApiExternaRetornaDadosFilme filmeDaApiExterna =
          omdbService.getFilmePorNome(filmeRequisicao.getNomeFilme());

      Filme filmeCriado = null;

      if (filmeDaApiExterna != null && filmeDaApiExterna.nomeFilme() != null) {
        filmeCriado =
            new Filme(
                null,
                null,
                null,
                filmeDaApiExterna.nomeFilme(),
                filmeDaApiExterna.sinopseFilme(),
                filmeDaApiExterna.categoriaFilme(),
                filmeDaApiExterna.anoFilme(),
                filmeDaApiExterna.diretorFilme());
      } else {
        filmeCriado =
            new Filme(
                null,
                null,
                null,
                filmeRequisicao.getNomeFilme(),
                filmeRequisicao.getSinopseFilme(),
                filmeRequisicao.getCategoriaFilme(),
                filmeRequisicao.getAnoFilme(),
                filmeRequisicao.getDiretorFilme());
      }

      filmeRepository.save(filmeCriado);

      retornoCriacaoDeFilme =
          new FilmeResultadoRetornaFilmeOuMensagem(
              filmeCriado.getIdPublico(),
              filmeCriado.getNomeFilme(),
              filmeCriado.getSinopseFilme(),
              filmeCriado.getCategoriaFilme(),
              filmeCriado.getAnoFilme(),
              filmeCriado.getDiretorFilme());
    }

    return retornoCriacaoDeFilme;
  }

  @Deprecated
  @Transactional
  public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 atualizarFilmeV1(
      FilmeDTOV1 filmeRequisicao) {

    FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoAtualizacaoFilme = null;

    Optional<Filme> filmeExistente = filmeRepository.findByIdLegado(filmeRequisicao.getIdLegado());

    if (filmeRequisicao.getNomeFilme() != null
        && filmeRequisicao.getIdLegado() != null
        && filmeExistente.isPresent()) {
      Filme filmeAtualizado = filmeExistente.get();
      filmeAtualizado.setNomeFilme(filmeRequisicao.getNomeFilme());
      filmeRepository.save(filmeAtualizado);

      List<FilmeDTOV1> listaDeFilmes = getListaDeFilmesVersaoUm();

      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
    } else if (filmeRequisicao.getNomeFilme() == null || filmeRequisicao.getIdLegado() == null) {
      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
              null, "O identificador e o nome do filme são obrigatórios.");
    } else {
      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
              null, "O registro não existe, faça um POST para inserir.");
    }

    return retornoAtualizacaoFilme;
  }

  @Transactional
  public FilmeResultadoRetornaFilmeOuMensagem atualizarFilmeV2(FilmeDTOV2 filmeRequisicao) {
    FilmeResultadoRetornaFilmeOuMensagem retornoAtualizacaoFilme = null;
    Optional<Filme> filmeExistente =
        filmeRepository.findByIdPublico(filmeRequisicao.getIdPublico());

    if (filmeRequisicao.getIdPublico() == null) {
      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaFilmeOuMensagem("É obrigatório informar o ID do filme.");
    } else if (filmeExistente.isPresent() && filmeRequisicao.getNomeFilme() != null) {
      Filme filmeatualizado = filmeExistente.get();
      filmeatualizado.setNomeFilme(filmeRequisicao.getNomeFilme());
      filmeatualizado.setSinopseFilme(filmeRequisicao.getSinopseFilme());
      filmeatualizado.setCategoriaFilme(filmeRequisicao.getCategoriaFilme());
      filmeatualizado.setAnoFilme(filmeRequisicao.getAnoFilme());
      filmeatualizado.setDiretorFilme(filmeRequisicao.getDiretorFilme());
      filmeRepository.save(filmeatualizado);

      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaFilmeOuMensagem(
              filmeatualizado.getIdPublico(),
              filmeatualizado.getNomeFilme(),
              filmeatualizado.getSinopseFilme(),
              filmeatualizado.getCategoriaFilme(),
              filmeatualizado.getAnoFilme(),
              filmeatualizado.getDiretorFilme());

    } else if (filmeExistente.isPresent() && filmeRequisicao.getNomeFilme() == null) {
      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaFilmeOuMensagem("O nome do filme é obrigatório.");
    } else {
      retornoAtualizacaoFilme =
          new FilmeResultadoRetornaFilmeOuMensagem(
              "Não há filme cadastrado com o ID: "
                  + filmeRequisicao.getIdPublico()
                  + ". Faça um POST para inserir.");
    }

    return retornoAtualizacaoFilme;
  }

  @Deprecated
  @Transactional
  public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 deletarFilmePeloIdV1(Integer idRequisicao) {

    FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoDeletarFilme = null;

    Optional<Filme> optionalFilme = filmeRepository.findByIdLegado(idRequisicao);

    if (optionalFilme.isPresent()) {
      filmeRepository.deleteByIdLegado(idRequisicao);

      List<FilmeDTOV1> listaDeFilmes = getListaDeFilmesVersaoUm();

      retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
    } else {
      retornoDeletarFilme =
          new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(
              null, "Identificador não encontrado! Não foi possível excluir o filme.");
    }

    return retornoDeletarFilme;
  }

  @Transactional
  public FilmeResultadoRetornaFilmeOuMensagem deletarFilmePeloIdV2(UUID idRequisicao) {
    FilmeResultadoRetornaFilmeOuMensagem retornoDeletarFilme = null;
    Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

    if (optionalFilme.isPresent()) {
      filmeRepository.deleteByIdPublico(idRequisicao);
      retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem("Filme excluído com sucesso!");
    } else {
      retornoDeletarFilme =
          new FilmeResultadoRetornaFilmeOuMensagem(
              "Identificador não encontrado! Não foi possível excluir o filme.");
    }

    return retornoDeletarFilme;
  }
}
