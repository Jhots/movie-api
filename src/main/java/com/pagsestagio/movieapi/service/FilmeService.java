package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecao;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FilmeService {

    private final Map<Integer, String> nomesDeFilmesPorId;

    public FilmeService(@Qualifier("bancoDados") Map<Integer, String> nomesDeFilmesPorId) {
        this.nomesDeFilmesPorId = nomesDeFilmesPorId;
    }

    private List<FilmeDTO> converterMapParaLista(Map<Integer, String> nomesDeFilmesPorId) {
        List<FilmeDTO> listaFilmes = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : nomesDeFilmesPorId.entrySet()) {
            Integer id = entry.getKey();
            String nomeFilme = entry.getValue();
            listaFilmes.add(new FilmeDTO(id, nomeFilme));
        }

        return listaFilmes;
    }

    public FilmeResultadoRetornaFilmeOuExcecao pegarFilmePeloId(Integer id){
        FilmeResultadoRetornaFilmeOuExcecao retornoDoFilmePorIdentificador = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecao(id, nomeDoFilmeEncontrado, null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuExcecao(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecao criarFilme(FilmeDTO filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoCriacaoDeFilme = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(null, "O identificador e o nome do filme devem ser inseridos.");
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(null, "O registro já existe, faça um PUT para atualizar.");
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoCriacaoDeFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(converterMapParaLista(nomesDeFilmesPorId), null);
        }

        return retornoCriacaoDeFilme;

    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecao atualizarFilme(FilmeDTO filme) {

        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoAtualizacaoFilme = null;

        if (filme.nomeFilme() != null && nomesDeFilmesPorId.containsKey(filme.identificador())) {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(converterMapParaLista(nomesDeFilmesPorId), null);
        } else if (filme.nomeFilme() == null && nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(null, "O identificador e o nome do filme são obrigatórios.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }

    public FilmeResultadoRetornaListaDeFilmesOuExcecao deletarFilmePeloId(Integer id){

        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoDeletarFilme = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(converterMapParaLista(nomesDeFilmesPorId), null);
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaListaDeFilmesOuExcecao(null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }
}
