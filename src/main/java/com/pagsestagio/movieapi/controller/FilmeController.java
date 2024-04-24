package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaErroRetornaMensagem;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaFilme;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaLista;
import com.pagsestagio.movieapi.model.*;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @Autowired
    @Qualifier("bancoDados")
    Map<Integer, String> nomesDeFilmesPorId;

    @Autowired
    public FilmeController(FilmeService filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResposta> pegarFilmePeloId(@PathVariable Integer id){
        filmeService.pegarFilmePeloId(id);

        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (resultado.exception() != null){
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaFilme(resultado.filme().identificador(), resultado.filme().nomeFilme()));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem(resultado.exception().getMessage()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody Filme filme) {
        FilmeResultado resultado = filmeService.criarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(resultado.exception() != null){
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem(resultado.exception().getMessage()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody Filme filme) {
        FilmeResultado resultado = filmeService.atualizarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(resultado.exception() != null){
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem(resultado.exception().getMessage()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultado resultado = filmeService.deletarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(resultado.exception() != null){
            respostaRequisicao = ResponseEntity.ok(new FilmeRespostaSucessoRetornaLista(nomesDeFilmesPorId));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaErroRetornaMensagem(resultado.exception().getMessage()));
        }
        return respostaRequisicao;
    }

}