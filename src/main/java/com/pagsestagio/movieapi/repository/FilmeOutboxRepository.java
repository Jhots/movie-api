package com.pagsestagio.movieapi.repository;

import com.pagsestagio.movieapi.model.FilmeOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeOutboxRepository extends JpaRepository<FilmeOutbox, Long> {

}
