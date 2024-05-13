package com.pagsestagio.movieapi.latestversion.controller.resposta;


import java.util.UUID;

public record FilmeRespostaRetornaFilmeOuMensagem(UUID idpublico, String nomeFilme, String mensagemStatus) implements FilmeResposta {}