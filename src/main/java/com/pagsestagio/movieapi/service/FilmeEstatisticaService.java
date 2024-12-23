package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.FilmeEstatistica;
import com.pagsestagio.movieapi.model.resultado.FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem;
import com.pagsestagio.movieapi.repository.FilmeEstatisticaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FilmeEstatisticaService {

    private final FilmeEstatisticaRepository filmeEstatisticaRepository;

    @Autowired
    public FilmeEstatisticaService(FilmeEstatisticaRepository filmeEstatisticaRepository) {
        this.filmeEstatisticaRepository = filmeEstatisticaRepository;
    }

    public FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem obterEstatisticas(UUID idPublico) {
        Optional<FilmeEstatistica> estatisticaOptional = filmeEstatisticaRepository.findById(idPublico);

        if (estatisticaOptional.isPresent()) {
            FilmeEstatistica estatistica = estatisticaOptional.get();
            return new FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem(
                    estatistica.getIdPublico(),
                    estatistica.getNomeFilme(),
                    estatistica.getContadorBuscas(),
                    null
            );
        } else {
            return new FilmeEstatisticaResultadoRetornaFilmeEstatisticaOuMensagem(
                    null,
                    null,
                    null,
                    "Não foram encontradas estatísticas para este filme"
            );
        }
    }
}
