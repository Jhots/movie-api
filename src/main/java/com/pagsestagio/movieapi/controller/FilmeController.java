package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.model.Filme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    // Criação de um map para relacionar cada nome de filme com um número identificador.
    Map<Integer, String> nomesDeFilmesPorId = new HashMap<>();

    //Criação de cada um dos métodos que irão tratar das requisições quanto à lista de filmes.
    @GetMapping("/{id}")
    public ResponseEntity<Object> mensagemRequisicaoGet(@PathVariable Integer id){
        if(nomesDeFilmesPorId.containsKey(id)){
            return ResponseEntity.ok(nomesDeFilmesPorId.get(id));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<Object> mensagemRequisicaoPost(@RequestBody Filme filme) {
        if (filme.identificador() == null || filme.nomeFilme() == null) {
            return ResponseEntity.badRequest().body("O identificador e o nome do filme devem ser inseridos.");
        }  else if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            return ResponseEntity.badRequest().body("O registro já existe, faça um PUT para atualizar.");
        } else {
            nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
            return ResponseEntity.ok(nomesDeFilmesPorId);
        }
    }


    @PutMapping
    public ResponseEntity<Object> mensagemRequisicaoPut(@RequestBody Filme filme) {
        if (nomesDeFilmesPorId.containsKey(filme.identificador())) {
            if(filme.nomeFilme() != null){
                nomesDeFilmesPorId.put(filme.identificador(), filme.nomeFilme());
                return ResponseEntity.ok(nomesDeFilmesPorId);
            } else {
                return ResponseEntity.badRequest().body("O identificador e o nome do filme são obrigatórios.");
            }
        } else {
            return ResponseEntity.badRequest().body("O registro não existe, faça um POST para inserir.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> mensagemRequisicaoDelete(@PathVariable Integer id){
        if(nomesDeFilmesPorId.containsKey(id)){
            nomesDeFilmesPorId.remove(id);
            return ResponseEntity.ok(nomesDeFilmesPorId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
