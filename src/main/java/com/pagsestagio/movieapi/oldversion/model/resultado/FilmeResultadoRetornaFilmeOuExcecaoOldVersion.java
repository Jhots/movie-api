package com.pagsestagio.movieapi.oldversion.model.resultado;

@Deprecated
public record FilmeResultadoRetornaFilmeOuExcecaoOldVersion(Integer id, String nomeFilme, String mensagemStatus) implements FilmeResultadoOldVersion {
}
