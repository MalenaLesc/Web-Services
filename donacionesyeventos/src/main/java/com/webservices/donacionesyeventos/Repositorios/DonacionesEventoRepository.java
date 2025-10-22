package com.webservices.donacionesyeventos.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.webservices.donacionesyeventos.Entidades.DonacionesEvento;

@Repository
public interface DonacionesEventoRepository extends JpaRepository<DonacionesEvento, Long>, JpaSpecificationExecutor<DonacionesEvento> {
}

