package com.webservices.donacionesyeventos.Servicios;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Entidades.DonacionesEvento;
import com.webservices.donacionesyeventos.Repositorios.DonacionesEventoRepository;
import com.webservices.donacionesyeventos.dtos.InformeDonacionesDTO;

@Service
public class InformeDonacionesServicio {

    @Autowired
    private DonacionesEventoRepository donacionesEventoRepository;

    public List<InformeDonacionesDTO> obtenerInforme(CategoriaDonacion categoria, LocalDateTime fechaInicio,LocalDateTime fechaFin,Boolean eliminado) {
        Specification<DonacionesEvento> spec = (root, query, cb) -> cb.conjunction();

        if (categoria != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("item").get("categoria"), categoria));
        }
        if (fechaInicio != null && fechaFin != null) {
            spec = spec.and((root, query, cb) ->
                cb.between(root.get("item").get("fechaHoraAlta"), fechaInicio, fechaFin));
        }
        if (eliminado != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("item").get("eliminado"), eliminado));
        }

        List<DonacionesEvento> resultados = donacionesEventoRepository.findAll(spec);

        // Agrupamiento por categoría y eliminado
        return resultados.stream()
            .collect(Collectors.groupingBy(
                d -> new AbstractMap.SimpleEntry<>(
                        d.getItem().getCategoria(),
                        d.getItem().isEliminado()),
                Collectors.summingInt(DonacionesEvento::getCantidad)
            ))
            .entrySet().stream()
            .map(e -> new InformeDonacionesDTO(
                    e.getKey().getKey(), // categoría
                    e.getKey().getValue(), // eliminado
                    e.getValue() // total
            ))
            .toList();
    }
    
}
