package com.pagsestagio.movieapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagsestagio.movieapi.IntegrationBaseTest;
import com.pagsestagio.movieapi.model.FilmeEstatistica;
import com.pagsestagio.movieapi.repository.FilmeEstatisticaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

public class FilmeEstatisticaControllerTests extends IntegrationBaseTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private FilmeEstatisticaRepository filmeEstatisticaRepository;

  private UUID idPublicoExistente;
  private final UUID idPublicoInexistente = UUID.fromString("f1ea8127-3766-470d-900a-0dc692fb1741");

  @BeforeEach
  public void setUp() {
    filmeEstatisticaRepository.deleteAll();

    idPublicoExistente = UUID.fromString("8bec4f7d-0dc5-4a93-a704-ccb44cc6fa2f");

    FilmeEstatistica filmeEstatistica = new FilmeEstatistica();
    filmeEstatistica.setIdPublico(idPublicoExistente);
    filmeEstatistica.setNomeFilme("Avatar");
    filmeEstatistica.setContadorBuscas(10);

    filmeEstatisticaRepository.save(filmeEstatistica);
  }

  @Test
  void deveRetornarOkQuandoIdPublicoExisteENaoPossuiMensagem() throws Exception {
    var requisicaoDeFilmeEstatisticaAtravesDeIdentificadorExistente =
        get("/v2/filmes/estatisticas/{idPublico}", idPublicoExistente)
            .contentType(MediaType.APPLICATION_JSON);

    var resultadoRequisicaoFilmeEstatisticaAtravesDeIdentificadorExistente =
        mockMvc.perform(requisicaoDeFilmeEstatisticaAtravesDeIdentificadorExistente);

    resultadoRequisicaoFilmeEstatisticaAtravesDeIdentificadorExistente
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idPublico").value(idPublicoExistente.toString()))
        .andExpect(jsonPath("$.nomeFilme").value("Avatar"))
        .andExpect(jsonPath("$.contadorBuscas").value(10));
  }

  @Test
  void deveRetornarNotFoundQuandoIdPublicoNaoExisteEPossuiMensagem() throws Exception {
    var requisicaoDeFilmeEstatisticaAtravesDeIdentificadorInexistente =
        get("/v2/filmes/estatisticas/{idPublico}", idPublicoInexistente)
            .contentType(MediaType.APPLICATION_JSON);

    var resultadoRequisicaoFilmeEstatisticaAtravesDeIdentificadorInexistente =
        mockMvc.perform(requisicaoDeFilmeEstatisticaAtravesDeIdentificadorInexistente);

    resultadoRequisicaoFilmeEstatisticaAtravesDeIdentificadorInexistente
        .andExpect(status().isNotFound())
        .andExpect(
            jsonPath("$.mensagemStatus")
                .value("Não foram encontradas estatísticas para este filme"));
  }
}
