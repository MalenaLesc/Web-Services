package com.webservices.donacionesyeventos.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservices.donacionesyeventos.Entidades.Usuario;
import com.webservices.donacionesyeventos.Repositorios.UsuarioRepository;

@Service
public class UsuarioServicio {
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Devuelve un usuario por su ID, o null si no existe.
     */
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    /**
     * Lista todos los usuarios.
     */
    public List<Usuario> getTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Guarda o actualiza un usuario.
     */
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por ID.
     */
    public boolean eliminar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Busca por nombre de usuario.
     */
    public Usuario getPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).orElse(null);
    }

    /**
     * Busca por email.
     */
    public Usuario getPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}
