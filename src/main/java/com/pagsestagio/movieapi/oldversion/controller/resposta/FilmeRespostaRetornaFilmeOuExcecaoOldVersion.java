package com.pagsestagio.movieapi.oldversion.controller.resposta;

@Deprecated
public record FilmeRespostaRetornaFilmeOuExcecaoOldVersion(Integer id, String nomeFilme, String mensagemStatus) implements FilmeRespostaOldVersion {

}
