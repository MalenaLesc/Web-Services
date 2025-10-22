package com.webservices.donacionesyeventos.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webservices.donacionesyeventos.Entidades.FiltroPersonalizado;
import com.webservices.donacionesyeventos.Entidades.Usuario;

@Repository
public interface FiltroPersonalizadoRepository extends JpaRepository<FiltroPersonalizado, Long> {
    List<FiltroPersonalizado> findByUsuario(Usuario usuario);
}