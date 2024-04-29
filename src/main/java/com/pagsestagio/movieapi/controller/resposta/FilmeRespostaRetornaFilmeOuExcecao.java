package com.pagsestagio.movieapi.controller.resposta;



public record FilmeRespostaRetornaFilmeOuExcecao(Integer id, String nomeFilme, String mensagemStatus) implements FilmeResposta {}