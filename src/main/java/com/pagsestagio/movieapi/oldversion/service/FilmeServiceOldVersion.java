package com.pagsestagio.movieapi.oldversion.service;

import com.pagsestagio.movieapi.oldversion.model.FilmeDTOOldVersion;
import com.pagsestagio.movieapi.oldversion.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoOldVersion;
import com.pagsestagio.movieapi.oldversion.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
@Service
public class FilmeServiceOldVersion {

    private final Map<Integer, String> nomesDeFilmesPorId;

    public FilmeServiceOldVersion(@Qualifier("bancoDados") Map<Integer, String> nomesDeFilmesPorId) {
        this.nomesDeFilmesPorId = nomesDeFilmesPorId;
    }

    private List<FilmeDTOOldVersion> converterMapParaLista(Map<Integer, String> nomesDeFilmesPorId) {
        List<FilmeDTOOldVersion> listaFilmes = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : nomesDeFilmesPorId.entrySet()) {
            Integer id = entry.getKey();
            String nomeFilme = entry.getValue();
            listaFilmes.add(new FilmeDTOOldVersion(id, nomeFilme));
        }

        return listaFilmes;
    }

    public FilmeResultadoRetornaFilmeOuExcecaoOldVersion pegarFilmePeloId(Integer id){
        FilmeResultadoRetornaFilmeOuExcecaoOldVersion retornoDoFilmePorIdentificador = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecaoOldVersion(id, nomeDoFilmeEncontrado, null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecaoOldVersion(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion criarFilme(FilmeDTOOldVersion filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoCriacaoDeFilme = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(null, "O identificador e o nome do filme devem ser inseridos.");
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(null, "O registro já existe, faça um PUT para atualizar.");
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(converterMapParaLista(nomesDeFilmesPorId), null);
        }

        return retornoCriacaoDeFilme;

    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion atualizarFilme(FilmeDTOOldVersion filme) {

        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoAtualizacaoFilme = null;

        if (filme.nomeFilme() != null && nomesDeFilmesPorId.containsKey(filme.identificador())) {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(converterMapParaLista(nomesDeFilmesPorId), null);
        } else if (filme.nomeFilme() == null && nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(null, "O identificador e o nome do filme são obrigatórios.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion deletarFilmePeloId(Integer id){

        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoDeletarFilme = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(converterMapParaLista(nomesDeFilmesPorId), null);
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion(null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }
}
