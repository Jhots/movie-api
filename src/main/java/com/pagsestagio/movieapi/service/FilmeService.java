package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.resultado.FilmeResultado;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilme;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaLista;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class FilmeService {

    private final Map<Integer, String> nomesDeFilmesPorId;

    public FilmeService(@Qualifier("bancoDados") Map<Integer, String> nomesDeFilmesPorId) {
        this.nomesDeFilmesPorId = nomesDeFilmesPorId;
    }

    public FilmeResultado pegarFilmePeloId(Integer id){
        FilmeResultado retornoDoFilmePorIdentificador = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilme(new Filme(id, nomeDoFilmeEncontrado), null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilme(null, new RuntimeException("Identificador não encontrado! Não foi possível obter o filme."));
        }

        return retornoDoFilmePorIdentificador;
    }

    public FilmeResultado criarFilme(Filme filme) {
        FilmeResultado retornoCriacaoDeFilme = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilme(null, new RuntimeException("O identificador e o nome do filme devem ser inseridos."));
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilme(null, new RuntimeException("O registro já existe, faça um PUT para atualizar."));
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            retornoCriacaoDeFilme = new FilmeResultadoRetornaLista((nomesDeFilmesPorId), null);
        }

        return retornoCriacaoDeFilme;

    }

    public FilmeResultado atualizarFilme(Filme filme) {

        FilmeResultado retornoAtualizacaoFilme = null;

        if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            if(filme.nomeFilme() != null){
                nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
                retornoAtualizacaoFilme = new FilmeResultadoRetornaLista((nomesDeFilmesPorId), null);
            } else {
                retornoAtualizacaoFilme = new FilmeResultadoRetornaFilme(null, new RuntimeException("O identificador e o nome do filme são obrigatórios."));
            }
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("O registro não existe, faça um POST para inserir."));
        }

        return retornoAtualizacaoFilme;

    }

    public FilmeResultado deletarFilmePeloId(Integer id){

        FilmeResultado retornoDeletarFilme = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            retornoDeletarFilme = new FilmeResultadoRetornaLista(nomesDeFilmesPorId, null);
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaLista(null, new RuntimeException("Identificador não encontrado! Não foi possível excluir o filme."));
        }

        return retornoDeletarFilme;

    }
}
