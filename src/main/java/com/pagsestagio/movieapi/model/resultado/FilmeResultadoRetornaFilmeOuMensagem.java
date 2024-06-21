package com.pagsestagio.movieapi.model.resultado;

import java.util.UUID;

public record FilmeResultadoRetornaFilmeOuMensagem(
    UUID idpublico,
    String nomeFilme,
    String sinopseFilme,
    String categoriaFilme,
    Integer anoFilme,
    String diretorFilme,
    String mensagemStatus)
    implements FilmeResultado {}
