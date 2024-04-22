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

    public ResponseEntity<FilmeResposta> pegarFilmePeloId(Integer id){

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaFilme(id, nomeDoFilmeEncontrado));
        } else {
            respostaRequisicao = ResponseEntity.notFound().build();
        }

        return respostaRequisicao;

    }

    public ResponseEntity<FilmeResposta> criarFilme(Filme filme) {

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem("O identificador e o nome do filme devem ser inseridos."));
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem("O registro já existe, faça um PUT para atualizar."));
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
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
