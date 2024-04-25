package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilme;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaLista;
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

    public List<Filme> converterMapParaLista(Map<Integer, String> nomesDeFilmesPorId) {
        List<Filme> listaFilmes = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : nomesDeFilmesPorId.entrySet()) {
            Integer id = entry.getKey();
            String nomeFilme = entry.getValue();
            listaFilmes.add(new Filme(id, nomeFilme));
        }

        return listaFilmes;
    }

    public FilmeResultadoRetornaFilme pegarFilmePeloId(Integer id){
        FilmeResultadoRetornaFilme retornoDoFilmePorIdentificador = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilme(new Filme(id, nomeDoFilmeEncontrado), null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilme(null, new RuntimeException("Identificador não encontrado! Não foi possível obter o filme."));
        }

        return retornoDoFilmePorIdentificador;
    }

    public FilmeResultadoRetornaLista criarFilme(Filme filme) {
        FilmeResultadoRetornaLista retornoCriacaoDeFilme = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("O identificador e o nome do filme devem ser inseridos."));
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("O registro já existe, faça um PUT para atualizar."));
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoCriacaoDeFilme = new FilmeResultadoRetornaLista(converterMapParaLista(nomesDeFilmesPorId), null);
        }

        return retornoCriacaoDeFilme;

    }

    public FilmeResultadoRetornaLista atualizarFilme(Filme filme) {

        FilmeResultadoRetornaLista retornoAtualizacaoFilme = null;

        if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            if(filme.nomeFilme() != null){
                nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
                retornoAtualizacaoFilme = new FilmeResultadoRetornaLista(converterMapParaLista(nomesDeFilmesPorId), null);
            } else {
                retornoAtualizacaoFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("O identificador e o nome do filme são obrigatórios."));
            }
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("O registro não existe, faça um POST para inserir."));
        }

        return retornoAtualizacaoFilme;

    }

    public FilmeResultadoRetornaLista deletarFilmePeloId(Integer id){

        FilmeResultadoRetornaLista retornoDeletarFilme = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            retornoDeletarFilme = new FilmeResultadoRetornaLista(converterMapParaLista(nomesDeFilmesPorId), null);
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("Identificador não encontrado! Não foi possível excluir o filme."));
        }

        return retornoDeletarFilme;

    }
}
