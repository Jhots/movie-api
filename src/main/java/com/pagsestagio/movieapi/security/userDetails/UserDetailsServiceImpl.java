package com.pagsestagio.movieapi.security.userDetails;

import com.pagsestagio.movieapi.model.Usuario;
import com.pagsestagio.movieapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario =
        usuarioRepository
            .findByNomeUsuario(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    return new UserDetailsImpl(usuario);
  }
}
