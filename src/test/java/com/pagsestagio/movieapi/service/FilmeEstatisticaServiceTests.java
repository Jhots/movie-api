package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.FilmeEstatistica;
import com.pagsestagio.movieapi.model.resultado.FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem;
import com.pagsestagio.movieapi.repository.FilmeEstatisticaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FilmeEstatisticaServiceTests {
  private FilmeEstatisticaRepository filmeEstatisticaRepository =
      Mockito.mock(FilmeEstatisticaRepository.class);
  private FilmeEstatisticaService filmeEstatisticaService =
      new FilmeEstatisticaService(filmeEstatisticaRepository);

  private UUID idPublico;
  private FilmeEstatistica filmeEstatistica;

  @BeforeEach
  public void setUp() {
    idPublico = UUID.randomUUID();
    filmeEstatistica = new FilmeEstatistica(idPublico, "The Godfather", 50);
  }

  @Test
  public void deveObterEstatisticasDoFilmeQuandoOFilmeEstaPresenteNoBanco() {
    Mockito.when(filmeEstatisticaRepository.findById(idPublico))
        .thenReturn(Optional.of(filmeEstatistica));

    FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem resultado =
        filmeEstatisticaService.obterEstatisticas(idPublico);

    assertNull(resultado.mensagemStatus());
    assertEquals(idPublico, resultado.idPublico());
    assertEquals("The Godfather", resultado.nomeFilme());
    assertEquals(50, resultado.contadorBuscas());
  }

  @Test
  public void deveRetornarMensagemDeEstatisticasNaoEncontradasQuandoOFilmeNaoEstaPresenteNoBanco() {
    Mockito.when(filmeEstatisticaRepository.findById(idPublico)).thenReturn(Optional.empty());

    FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem resultado =
        filmeEstatisticaService.obterEstatisticas(idPublico);

    assertNotNull(resultado.mensagemStatus());
    assertEquals("Não foram encontradas estatísticas para este filme", resultado.mensagemStatus());
  }
}
