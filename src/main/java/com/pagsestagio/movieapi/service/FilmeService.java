package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaErroRetornaMensagem;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaFilme;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaLista;
import com.pagsestagio.movieapi.model.Filme;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class FilmeService {

    private final Map<Integer, String> nomesDeFilmesPorId;

    public FilmeService(@Qualifier("bancoDados") Map<Integer, String> nomesDeFilmesPorId) {
        this.nomesDeFilmesPorId = nomesDeFilmesPorId;
    }

    public FilmeResposta pegarFilmePeloId(Integer id){
        FilmeResposta retornoRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            retornoRequisicao = new FilmeRespostaSucessoRetornaFilme(id, nomeDoFilmeEncontrado);
        } else {
            retornoRequisicao = new FilmeRespostaErroRetornaMensagem("Filme não encontrado!");
        }

        return retornoRequisicao;

    }

    public FilmeResposta criarFilme(Filme filme) {
        //Se mandar um filme sem id e nome deve retornar bad request com a string "O identificador e o nome do filme devem ser inseridos".
        //Se mandar um filme com id que já está contido na lista, deve retornar bad request com a string "O registro já existe, faça um PUT para atualizar".
        //Se nenhum dos casos acima acontecerem, deve ser inserido o id e nome do filme na lista e retornar ok.
        FilmeResposta respostaRequisicao = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            respostaRequisicao = new FilmeRespostaErroRetornaMensagem("O identificador e o nome do filme devem ser inseridos.");
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            respostaRequisicao = new FilmeRespostaErroRetornaMensagem("O registro já existe, faça um PUT para atualizar.");
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            respostaRequisicao = new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId);
        }

        return respostaRequisicao;

    }

    public ResponseEntity<FilmeResposta> atualizarFilme(Filme filme) {

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            if(filme.nomeFilme() != null){
                nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
                respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
            } else {
                respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem("O identificador e o nome do filme são obrigatórios."));
            }
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem("O registro não existe, faça um POST para inserir."));
        }

        return respostaRequisicao;

    }

    public ResponseEntity<FilmeResposta> deletarFilmePeloId(Integer id){

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
        } else {
            respostaRequisicao = ResponseEntity.notFound().build();
        }

        return respostaRequisicao;

    }
}
