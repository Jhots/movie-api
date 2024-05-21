package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaFilmeOuExcecaoV1;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaListaDeFilmesOuExcecaoV1;
import com.pagsestagio.movieapi.model.FilmeDTOV1;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoV1;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecaoV1;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/filmes")
public class FilmeControllerV1 {

    private final FilmeService filmeService;

    @Autowired
    public FilmeControllerV1(FilmeService filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeResposta> pegarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaFilmeOuExcecaoV1 retornoService = filmeService.pegarFilmePeloIdV1(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuExcecaoV1(retornoService.id(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaFilmeOuExcecaoV1(null,null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody FilmeDTOV1 filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoService = filmeService.criarFilmeV1(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody FilmeDTOV1 filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoService = filmeService.atualizarFilmeV1(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;
        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaListaDeFilmesOuExcecaoV1 retornoService = filmeService.deletarFilmePeloIdV1(id);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoV1(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

}
