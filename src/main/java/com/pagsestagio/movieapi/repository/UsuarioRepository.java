package com.pagsestagio.movieapi.repository;

import com.pagsestagio.movieapi.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
