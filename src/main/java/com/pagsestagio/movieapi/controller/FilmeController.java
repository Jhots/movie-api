package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.model.*;
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
        return filmeService.pegarFilmePeloId(id);
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody Filme filme) {
        return filmeService.criarFilme(filme);
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody Filme filme) {
        return filmeService.atualizarFilme(filme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable Integer id){
        return filmeService.deletarFilmePeloId(id);
    }

}