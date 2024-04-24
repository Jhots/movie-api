package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaErroRetornaMensagem;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaFilme;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaLista;
import com.pagsestagio.movieapi.model.*;
import com.pagsestagio.movieapi.model.resultado.FilmeResultado;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilme;
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
    public FilmeController(FilmeService filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResposta> pegarFilmePeloId(@PathVariable Integer id){
        FilmeResultado retornoService = filmeService.pegarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (){
            respostaRequisicao = ResponseEntity.ok().body();
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body();
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody Filme filme) {
        FilmeResultado retornoService = filmeService.criarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(){
            respostaRequisicao = ResponseEntity.ok().body();
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body();
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody Filme filme) {
        FilmeResultado retornoService = filmeService.atualizarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(){
            respostaRequisicao = ResponseEntity.ok().body());
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body();
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultado retornoService = filmeService.deletarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(){
            respostaRequisicao = ResponseEntity.ok().body();
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body();
        }
        return respostaRequisicao;
    }

}