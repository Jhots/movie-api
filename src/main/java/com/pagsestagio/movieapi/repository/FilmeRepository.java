package com.pagsestagio.movieapi.repository;

import com.pagsestagio.movieapi.model.Filme;
import com.pagsestagio.movieapi.model.FilmeDTOV1;
import com.pagsestagio.movieapi.model.FilmeDTOV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    Optional<Filme> findByNomeFilme(String nome);

    Optional<Filme> findByIdPublico(UUID idPublico);

    @Deprecated
    Optional<Filme> findByIdLegado(Integer idLegado);

    void deleteByIdLegado(Integer idLegado);

    void deleteByIdPublico(UUID idPublico);

    boolean existsByIdLegado(Integer idLegado);
}
