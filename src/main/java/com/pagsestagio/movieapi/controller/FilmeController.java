package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeResposta;
import com.pagsestagio.movieapi.model.FilmeRespostaError;
import com.pagsestagio.movieapi.model.FilmeRespostaSuccess;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    Map<Integer, String> nomesDeFilmesPorId = new HashMap<>();

    @GetMapping("/{id}")
    public ResponseEntity<Object> mensagemRequisicaoGet(@PathVariable Integer id){

        ResponseEntity<Object> respostaRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            respostaRequisicao = ResponseEntity.ok(nomesDeFilmesPorId.get(id));
        } else {
            respostaRequisicao = ResponseEntity.notFound().build();
        }

        return respostaRequisicao;

    }

    @PostMapping
    public ResponseEntity<FilmeResposta> mensagemRequisicaoPost(@RequestBody Filme filme) {

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (filme.identificador() == null || filme.nomeFilme() == null) {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaError("O identificador e o nome do filme devem ser inseridos."));
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaError("O registro já existe, faça um PUT para atualizar."));
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSuccess(nomesDeFilmesPorId));
        }

        return respostaRequisicao;

    }

    @PutMapping
    public ResponseEntity<Object> mensagemRequisicaoPut(@RequestBody Filme filme) {

        ResponseEntity<Object> respostaRequisicao = null;

        if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            if(filme.nomeFilme() != null){
                nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
                respostaRequisicao = ResponseEntity.ok(nomesDeFilmesPorId);
            } else {
                respostaRequisicao = ResponseEntity.badRequest().body("O identificador e o nome do filme são obrigatórios.");
            }
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body("O registro não existe, faça um POST para inserir.");
        }

        return respostaRequisicao;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> mensagemRequisicaoDelete(@PathVariable Integer id){

        ResponseEntity<Object> respostaRequisicao = null;

        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            respostaRequisicao = ResponseEntity.ok(nomesDeFilmesPorId);
        } else {
            respostaRequisicao = ResponseEntity.notFound().build();
        }

        return respostaRequisicao;

    }
}