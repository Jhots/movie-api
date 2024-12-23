package com.pagsestagio.movieapi.repository;

import com.pagsestagio.movieapi.model.FilmeEstatistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FilmeEstatisticaRepository extends JpaRepository<FilmeEstatistica, UUID> {
}
