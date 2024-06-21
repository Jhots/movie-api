package com.pagsestagio.movieapi.controller.resposta;

import java.util.UUID;

public record FilmeRespostaRetornaFilmeOuMensagem(
    UUID idpublico,
    String nomeFilme,
    String sinopseFilme,
    String categoriaFilme,
    Integer anoFilme,
    String diretorFilme,
    String mensagemStatus)
    implements FilmeResposta {}
