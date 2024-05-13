package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @Autowired
    public FilmeController(FilmeService filmeService){
        this.filmeService = filmeService;
    }

    @GetMapping("/{idPublico}")
    public ResponseEntity<FilmeResposta> pegarFilmePeloId(@PathVariable UUID idPublico){
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.pegarFilmePeloId(idPublico);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(retornoService.idpublico(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PostMapping
    public ResponseEntity<FilmeResposta> criarFilme(@RequestBody FilmeDTO filme) {
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.criarFilme(filme);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus() == null){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(retornoService.idpublico(), retornoService.nomeFilme(), null));
        } else {
            respostaRequisicao = ResponseEntity.badRequest().body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

    @PutMapping
    public ResponseEntity<FilmeResposta> atualizarFilme(@RequestBody FilmeDTO filme) {
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.atualizarFilme(filme);
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
        FilmeResultadoRetornaFilmeOuMensagem retornoService = filmeService.deletarFilmePeloId(idPublico);
        ResponseEntity<FilmeResposta> respostaRequisicao = null;

        if(retornoService.mensagemStatus().equals("Filme excluído com sucesso!")){
            respostaRequisicao = ResponseEntity.ok().body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        } else {
            respostaRequisicao = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FilmeRespostaRetornaFilmeOuMensagem(null, null, retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }

}