package com.webservices.donacionesyeventos.Repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Entidades.InventarioDonaciones;

@Repository
public interface InventarioDonacionesRepository extends JpaRepository<InventarioDonaciones, Long> {

    List<InventarioDonaciones> findByCategoria(CategoriaDonacion categoria);

    List<InventarioDonaciones> findByEliminado(boolean eliminado);

    List<InventarioDonaciones> findByFechaHoraAltaBetween(LocalDateTime inicio, LocalDateTime fin);
}