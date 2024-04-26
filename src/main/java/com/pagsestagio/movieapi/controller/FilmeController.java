package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaFilme;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaSucessoRetornaLista;
import com.pagsestagio.movieapi.model.*;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilme;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaLista;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
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
        FilmeResultadoRetornaFilme retornoService = filmeService.pegarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (retornoService.excecao() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaSucessoRetornaFilme(retornoService.filme(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaSucessoRetornaFilme(null, retornoService.excecao()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody Filme filme) {
        FilmeResultadoRetornaLista retornoService = filmeService.criarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.excecao() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaSucessoRetornaLista(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaSucessoRetornaLista(null, retornoService.excecao()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody Filme filme) {
        FilmeResultadoRetornaLista retornoService = filmeService.atualizarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;
// excecao == null
        if(retornoService.excecao() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaSucessoRetornaLista(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaSucessoRetornaLista(null, retornoService.excecao()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaLista retornoService = filmeService.deletarFilmePeloId(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.excecao() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaSucessoRetornaLista(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaSucessoRetornaLista(null, retornoService.excecao()));
        }
        return respostaRequisicao;
    }

}