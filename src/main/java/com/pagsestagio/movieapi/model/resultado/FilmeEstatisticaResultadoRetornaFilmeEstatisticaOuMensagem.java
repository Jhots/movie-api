package com.pagsestagio.movieapi.model.resultado;

import java.util.UUID;

public record FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem(
        UUID idPublico,
        String nomeFilme,
        Integer contadorBuscas,
        String mensagemStatus)
        implements FilmeEstatisticaResultado {
    public FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem(UUID idPublico, String nomeFilme, Integer contadorBuscas) {
        this(idPublico, nomeFilme, contadorBuscas, null);
    }

    public FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem(String mensagemStatus) {
        this(null, null, null, mensagemStatus);
    }
}
