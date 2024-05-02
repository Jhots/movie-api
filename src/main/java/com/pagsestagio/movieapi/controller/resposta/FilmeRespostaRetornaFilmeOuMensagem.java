package com.pagsestagio.movieapi.controller.resposta;



public record FilmeRespostaRetornaFilmeOuMensagem(Integer id, String nomeFilme, String mensagemStatus) implements FilmeResposta {}