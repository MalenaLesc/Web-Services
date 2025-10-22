package com.webservices.donacionesyeventos.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.webservices.donacionesyeventos.Entidades.EventoSolidario;

@Repository
public interface EventoRepository extends JpaRepository<EventoSolidario, Long>, JpaSpecificationExecutor<EventoSolidario> {
    @EntityGraph(attributePaths = "participantesEvento")
    List<EventoSolidario> findAll();
}
