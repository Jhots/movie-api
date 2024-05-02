package com.pagsestagio.movieapi.model.resultado;


public record FilmeResultadoRetornaFilmeOuMensagem(Integer id, String nomeFilme, String mensagemStatus) implements FilmeResultado {
}
