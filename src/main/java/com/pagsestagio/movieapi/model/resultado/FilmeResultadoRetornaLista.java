package com.pagsestagio.movieapi.model.resultado;

import java.util.Map;

public record FilmeResultadoRetornaLista(Map<Integer, String> listaDeFilmes, Exception excecao) implements FilmeResultado  {
}
