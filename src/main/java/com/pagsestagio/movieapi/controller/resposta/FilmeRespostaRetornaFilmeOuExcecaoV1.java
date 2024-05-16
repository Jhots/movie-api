package com.pagsestagio.movieapi.controller.resposta;


@Deprecated
public record FilmeRespostaRetornaFilmeOuExcecaoV1(Integer idLegado, String nomeFilme, String mensagemStatus) implements FilmeResposta {

}
