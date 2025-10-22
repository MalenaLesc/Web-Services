package com.webservices.donacionesyeventos.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webservices.donacionesyeventos.Entidades.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    // Buscar por email
    Optional<Usuario> findByEmail(String email);
}
