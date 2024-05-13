package com.pagsestagio.movieapi.latestversion.service;

import com.pagsestagio.movieapi.latestversion.model.Filme;
import com.pagsestagio.movieapi.latestversion.model.FilmeDTO;
import com.pagsestagio.movieapi.latestversion.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.latestversion.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public FilmeResultadoRetornaFilmeOuMensagem pegarFilmePeloId(UUID idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDoFilmePorIdentificador = null;
        Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

        if(optionalFilme.isPresent()){
            Filme filmeprocurado = optionalFilme.get();
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(filmeprocurado.getIdPublico(), filmeprocurado.getNome(), null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }

    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem criarFilme(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoCriacaoDeFilme = null;

        Optional<Filme> filmeExistente = filmeRepository.findByNome(filmeRequisicao.nomeFilme());

        if (filmeRequisicao.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O nome do filme é obrigatório.");
        }  else if (filmeExistente.isPresent()) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O registro já existe, faça um PUT para atualizar.");
        } else {
            Filme filmecriado = new Filme();
            filmecriado.setNome(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmecriado);
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmecriado.getIdPublico(), filmecriado.getNome(), null);
        }

        return retornoCriacaoDeFilme;

    }

    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem atualizarFilme(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoAtualizacaoFilme = null;
        Optional<Filme> filmeExistente = filmeRepository.findByIdPublico(filmeRequisicao.idPublico());

        if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() != null) {
            Filme filmeatualizado = filmeExistente.get();
            filmeatualizado.setNome(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmeatualizado);

            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmeatualizado.getIdPublico(), filmeatualizado.getNome(), null);

        } else if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() == null) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O nome do filme é obrigatório.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }

    @Transactional
    public FilmeResultadoRetornaFilmeOuMensagem deletarFilmePeloId(UUID idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDeletarFilme = null;
        Optional<Filme> optionalFilme = filmeRepository.findByIdPublico(idRequisicao);

        if(optionalFilme.isPresent()){
            filmeRepository.deleteByIdPublico(idRequisicao);
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "Filme excluído com sucesso!");
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null,  null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }
}
