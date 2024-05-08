package com.pagsestagio.movieapi.repository;

import com.pagsestagio.movieapi.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {
    Optional<Filme> findByNome(String nome);
    Optional<Filme> findByIdPublico(UUID idPublico);
    void deleteByIdPublico(UUID idPublico);
}
