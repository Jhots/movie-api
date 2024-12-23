package com.pagsestagio.movieapi.controller.resposta;

import java.util.UUID;

public record FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(
        UUID idPublico,
        String nomeFilme,
        Integer contadorBuscas,
        String mensagemStatus) implements FilmeEstatisticaResposta {
    public FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(UUID idPublico, String nomeFilme, Integer contadorBuscas) {
        this(idPublico, nomeFilme, contadorBuscas, null);
    }

    public FilmeEstatisticaRespostaRetornaFilmeEstatisticaOuMensagem(String mensagemStatus) {
        this(null, null, null, mensagemStatus);
    }
}
