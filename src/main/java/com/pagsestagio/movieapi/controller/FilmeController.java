package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaFilmeOuExcecao;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaListaDeFilmesOuExcecao;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecao;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecao;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
        FilmeResultadoRetornaFilmeOuExcecao retornoService = filmeService.pegarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuExcecao(retornoService.id(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody FilmeDTO filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoService = filmeService.criarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody FilmeDTO filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoService = filmeService.atualizarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;
        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaListaDeFilmesOuExcecao retornoService = filmeService.deletarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaListaDeFilmesOuExcecao(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

}