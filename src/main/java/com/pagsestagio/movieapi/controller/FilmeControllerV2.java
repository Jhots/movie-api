package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import com.pagsestagio.movieapi.service.FilmeService;
import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("v2/filmes")
public class FilmeControllerV2 {

    private final FilmeService filmeService;

    @Autowired
    public FilmeControllerV2(FilmeService filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{idPublico}")
    public ResponseEntity<FilmeResposta> pegarFilmePeloId(@PathVariable UUID idPublico){
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.pegarFilmePeloIdV2(idPublico);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(retornoService.idpublico(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody FilmeDTOV2 filme) {
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.criarFilmeV2(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(retornoService.idpublico(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody FilmeDTOV2 filme) {
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.atualizarFilmeV2(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;
        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(retornoService.idpublico(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @DeleteMapping("/{idPublico}")
    public ResponseEntity<FilmeResposta> deletarFilmePeloId(@PathVariable UUID idPublico){
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.deletarFilmePeloIdV2(idPublico);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus().equals("Filme excluído com sucesso!")){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

}