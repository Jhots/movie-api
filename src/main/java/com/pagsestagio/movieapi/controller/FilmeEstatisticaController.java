package com.pagsestagio.movieapi.controller;

import com.pagsestagio.movieapi.controller.resposta.FilmeEstatisticaResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem;
import com.pagsestagio.movieapi.controller.resposta.FilmeResposta;
import com.pagsestagio.movieapi.controller.resposta.FilmeRespostaRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.model.resultado.FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.service.FilmeEstatisticaService;
import com.pagsestagio.movieapi.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("v2/filmes/estatisticas")
public class FilmeEstatisticaController {

    private final FilmeEstatisticaService filmeEstatisticaService;

    @Autowired
    public FilmeEstatisticaController(FilmeEstatisticaService filmeEstatisticaService) {
        this.filmeEstatisticaService = filmeEstatisticaService;
    }

    @GetMapping("/{idPublico}")
    public ResponseEntity<FilmeEstatisticaResposta> pegarFilmeEstatisticasPeloId(@PathVariable UUID idPublico) {
        FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem retornoService =
                filmeEstatisticaService.obterEstatisticas(idPublico);
        ResponseEntity<FilmeEstatisticaResposta> respostaRequisicao = null;

        if (retornoService.mensagemStatus() == null) {
            respostaRequisicao =
                    ResponseEntity.ok()
                            .body(
                                    new FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(
                                            retornoService.idPublico(),
                                            retornoService.nomeFilme(),
                                            retornoService.contadorBuscas()));
        } else {
            respostaRequisicao =
                    ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(retornoService.mensagemStatus()));
        }
        return respostaRequisicao;
    }
}
