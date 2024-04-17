package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    Map<Integer, String> nomesDeFilmesPorId = new HashMap<>();

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResposta> mensagemRequisicaoGet(@PathVariable Integer id){

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            String nomeDoFilmeEncontrado = nomesDeFilmesPorId.get(id);
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaFilme(id, nomeDoFilmeEncontrado));
        } else {
            respostaRequisicao = ResponseEntity.notFound().build();
        }

        return respostaRequisicao;

    }

    @PostMapping
    public ResponseEntity<FilmeResposta> mensagemRequisicaoPost(@RequestBody Filme filme) {

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

    @PutMapping
    public ResponseEntity<FilmeResposta> mensagemRequisicaoPut(@RequestBody Filme filme) {

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

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> mensagemRequisicaoDelete(@PathVariable Integer id){

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