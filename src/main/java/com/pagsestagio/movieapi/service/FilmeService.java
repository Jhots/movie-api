package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoV1;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecaoV1;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    List<Filme> listaDeFilmes = new ArrayList<>();

    public static List<Filme> converterFilmeParaListaDeFilmes(Filme filme) {
        List<Filme> listaDeFilmes = new ArrayList<>();
        if (filme != null) {
            listaDeFilmes.add(filme);
        }
        return listaDeFilmes;
    }

    @Deprecated
    public FilmeResultadoRetornaFilmeOuExcecaoV1 pegarFilmePeloIdV1(Integer idRequisicao){
        FilmeResultadoRetornaFilmeOuExcecaoV1 retornoDoFilmePorIdentificador = null;

        Optional<Filme> optionalFilme = filmeRepository.findByIdLegado(idRequisicao);

        if(optionalFilme.isPresent()){
            String nomeDoFilmeEncontrado = optionalFilme.get().getNomeFilme();
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecaoV1(optionalFilme.get().getId(), nomeDoFilmeEncontrado, null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecaoV1(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }


    public FilmeResultadoRetornaFilmeOuMensagem pegarFilmePeloIdV2(UUID idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDoFilmePorIdentificador = null;
        Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

        if(optionalFilme.isPresent()){
            Filme filmeprocurado = optionalFilme.get();
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(filmeprocurado.getIdPublico(), filmeprocurado.getNomeFilme(), null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }

    @Deprecated
    @Transactional
    public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 criarFilmeV1(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoCriacaoDeFilme = null;

        Optional<Filme> filmeExistente = filmeRepository.findByNomeFilme(filmeRequisicao.nomeFilme());

        if (filmeRequisicao.idLegado() == null || filmeRequisicao.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(null, "O identificador e o nome do filme devem ser inseridos.");
        }  else if (filmeExistente.isPresent()) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(null, "O registro já existe, faça um PUT para atualizar.");
        } else {
            Filme filmecriado = new Filme();
            filmecriado.setIdLegado(filmeRequisicao.idLegado());
            filmecriado.setNomeFilme(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmecriado);

            listaDeFilmes.add(filmecriado);

            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
        }

        return retornoCriacaoDeFilme;

    }



    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem criarFilmeV2(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoCriacaoDeFilme = null;

        Optional<Filme> filmeExistente = filmeRepository.findByNomeFilme(filmeRequisicao.nomeFilme());

        if (filmeRequisicao.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O nome do filme é obrigatório.");
        }  else if (filmeExistente.isPresent()) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O registro já existe, faça um PUT para atualizar.");
        } else {
            Filme filmecriado = new Filme();
            filmecriado.setNomeFilme(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmecriado);
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmecriado.getIdPublico(), filmecriado.getNomeFilme(), null);
        }

        return retornoCriacaoDeFilme;

    }

    @Deprecated
    @Transactional
    public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 atualizarFilmeV1(FilmeDTO filmeRequisicao) {

        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoAtualizacaoFilme = null;

        Optional<Filme> filmeExistente = filmeRepository.findByIdLegado(filmeRequisicao.idLegado());

        if (filmeRequisicao.nomeFilme() != null && filmeExistente.isPresent()) {
            Filme filmeatualizado = filmeExistente.get();
            filmeatualizado.setNomeFilme(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmeatualizado);

            listaDeFilmes.add(filmeatualizado);

            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
        } else if (filmeRequisicao.nomeFilme() == null || filmeRequisicao.idLegado() == null) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(null, "O identificador e o nome do filme são obrigatórios.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }


    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem atualizarFilmeV2(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoAtualizacaoFilme = null;
        Optional<Filme> filmeExistente = filmeRepository.findByIdPublico(filmeRequisicao.idPublico());

        if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() != null) {
            Filme filmeatualizado = filmeExistente.get();
            filmeatualizado.setNomeFilme(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmeatualizado);

            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmeatualizado.getIdPublico(), filmeatualizado.getNomeFilme(), null);

        } else if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() == null) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O nome do filme é obrigatório.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }

    @Deprecated
    @Transactional
    public FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 deletarFilmePeloIdV1(Integer idRequisicao){

        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoDeletarFilme = null;

        Optional<Filme> optionalFilme = filmeRepository.findByIdLegado(idRequisicao);

        if(optionalFilme.isPresent()){
            filmeRepository.deleteByIdLegado(idRequisicao);
            Filme filme = optionalFilme.get();
            listaDeFilmes.remove(filme);
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(listaDeFilmes, null);
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoV1(null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }

    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem deletarFilmePeloIdV2(UUID idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDeletarFilme = null;
        Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

        if(optionalFilme.isPresent()){
            filmeRepository.deleteByIdPublico(idRequisicao);
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null,null, "Filme excluído com sucesso!");
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null,  null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }
}
