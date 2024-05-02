package com.pagsestagio.movieapi.service;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTO;
import com.pagsestagio.movieapi.model.resultado.FilmeResultadoRetornaFilmeOuMensagem;
import com.pagsestagio.movieapi.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public FilmeResultadoRetornaFilmeOuMensagem pegarFilmePeloId(Integer idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDoFilmePorIdentificador = null;
        Optional<Filme> optionalFilme = filmeRepository.findById(idRequisicao);

        if(optionalFilme.isPresent()){
            Filme filmeprocurado = optionalFilme.get();
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(filmeprocurado.getId(), filmeprocurado.getNome(), null);
        } else {
            retornoDoFilmePorIdentificador = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "Identificador não encontrado! Não foi possível obter o filme.");
        }

        return retornoDoFilmePorIdentificador;
    }

    public FilmeResultadoRetornaFilmeOuMensagem criarFilme(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoCriacaoDeFilme = null;

        Optional<Filme> filmeExistente = filmeRepository.findById(filmeRequisicao.identificador());

        if (filmeRequisicao.identificador() == null || filmeRequisicao.nomeFilme() == null) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O identificador e o nome do filme devem ser inseridos.");
        }  else if (filmeExistente.isPresent()) {
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O registro já existe, faça um PUT para atualizar.");
        } else {
            Filme filmecriado = new Filme();
            filmecriado.setId(filmeRequisicao.identificador());
            filmecriado.setNome(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmecriado);
            retornoCriacaoDeFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmecriado.getId(), filmecriado.getNome(), null);
        }

        return retornoCriacaoDeFilme;

    }

    public FilmeResultadoRetornaFilmeOuMensagem atualizarFilme(FilmeDTO filmeRequisicao) {
        FilmeResultadoRetornaFilmeOuMensagem retornoAtualizacaoFilme = null;
        Optional<Filme> filmeExistente = filmeRepository.findById(filmeRequisicao.identificador());

        if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() != null) {
            Filme filmeatualizado = filmeExistente.get();

            filmeatualizado.setNome(filmeRequisicao.nomeFilme());
            filmeRepository.save(filmeatualizado);

            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(filmeatualizado.getId(), filmeatualizado.getNome(), null);


        } else if (filmeExistente.isPresent() && filmeRequisicao.nomeFilme() == null) {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null,"O identificador e o nome do filme são obrigatórios.");
        } else {
            retornoAtualizacaoFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "O registro não existe, faça um POST para inserir.");
        }

        return retornoAtualizacaoFilme;
    }

    public FilmeResultadoRetornaFilmeOuMensagem deletarFilmePeloId(Integer idRequisicao){
        FilmeResultadoRetornaFilmeOuMensagem retornoDeletarFilme = null;
        Optional<Filme> optionalFilme = filmeRepository.findById(idRequisicao);

        if(optionalFilme.isPresent()){
            filmeRepository.deleteById(idRequisicao);
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null, null, "Filme excluído com sucesso!");
        } else {
            retornoDeletarFilme = new FilmeResultadoRetornaFilmeOuMensagem(null,  null, "Identificador não encontrado! Não foi possível excluir o filme.");
        }

        return retornoDeletarFilme;

    }
}
