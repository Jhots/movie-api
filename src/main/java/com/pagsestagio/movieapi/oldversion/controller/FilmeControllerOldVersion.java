package com.pagsestagio.movieapi.oldversion.controller;

import com.pagsestagio.movieapi.oldversion.controller.resposta.FilmeRespostaOldVersion;
import com.pagsestagio.movieapi.oldversion.controller.resposta.FilmeRespostaRetornaFilmeOuExcecaoOldVersion;
import com.pagsestagio.movieapi.oldversion.controller.resposta.FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion;
import com.pagsestagio.movieapi.oldversion.model.FilmeDTOOldVersion;
import com.pagsestagio.movieapi.oldversion.model.resultado.FilmeResultadoRetornaFilmeOuExcecaoOldVersion;
import com.pagsestagio.movieapi.oldversion.model.resultado.FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion;
import com.pagsestagio.movieapi.oldversion.service.FilmeServiceOldVersion;
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
@RequestMapping("oldversion/filmes")
public class FilmeControllerOldVersion {

    private final FilmeServiceOldVersion filmeService;

    @Autowired
    public FilmeControllerOldVersion(FilmeServiceOldVersion filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmeRespostaOldVersion> pegarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaFilmeOuExcecaoOldVersion retornoService = filmeService.pegarFilmePeloId(id);
        ResponseEntity<FilmeRespostaOldVersion> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuExcecaoOldVersion(retornoService.id(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeRespostaOldVersion> criarFilme(@RequestBody FilmeDTOOldVersion filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoService = filmeService.criarFilme(filme);
        ResponseEntity<FilmeRespostaOldVersion> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeRespostaOldVersion> atualizarFilme(@RequestBody FilmeDTOOldVersion filme) {
        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoService = filmeService.atualizarFilme(filme);
        ResponseEntity<FilmeRespostaOldVersion> respostaRequisicao = null;
        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeRespostaOldVersion> deletarFilmePeloId(@PathVariable Integer id){
        FilmeResultadoRetornaListaDeFilmesOuExcecaoOldVersion retornoService = filmeService.deletarFilmePeloId(id);
        ResponseEntity<FilmeRespostaOldVersion> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(retornoService.listaDeFilmes(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaListaDeFilmesOuExcecaoOldVersion(null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

}
