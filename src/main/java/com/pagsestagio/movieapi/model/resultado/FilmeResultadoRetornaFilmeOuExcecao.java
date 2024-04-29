package com.pagsestagio.movieapi.model.resultado;


public record FilmeResultadoRetornaFilmeOuExcecao(Integer id, String nomeFilme, String mensagemStatus) implements FilmeResultado {
}
