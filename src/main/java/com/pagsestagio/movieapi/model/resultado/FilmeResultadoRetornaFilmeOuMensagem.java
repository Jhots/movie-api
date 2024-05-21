package com.pagsestagio.movieapi.model.resultado;

import java.util.UUID;

public record FilmeResultadoRetornaFilmeOuMensagem(UUID idpublico, String nomeFilme, String mensagemStatus) implements FilmeResultado {
}
