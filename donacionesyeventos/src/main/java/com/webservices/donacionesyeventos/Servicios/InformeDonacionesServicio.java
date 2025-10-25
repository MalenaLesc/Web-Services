package com.webservices.donacionesyeventos.Servicios;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Entidades.DonacionesEvento;
import com.webservices.donacionesyeventos.Repositorios.DonacionesEventoRepository;
import com.webservices.donacionesyeventos.dtos.InformeDonacionesDTO;

@Service
public class InformeDonacionesServicio {

    @Autowired
    private DonacionesEventoRepository donacionesEventoRepository;

    public List<InformeDonacionesDTO> obtenerInforme(
            CategoriaDonacion categoria,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Boolean eliminado) {

        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }


        List<DonacionesEvento> todas = donacionesEventoRepository.findAll();

        return todas.stream()
                .filter(d -> d.getItem() != null)
                .filter(d -> categoria == null || d.getItem().getCategoria() == categoria)
                .filter(d -> eliminado == null || d.getItem().isEliminado() == eliminado)
                .filter(d -> {
                    LocalDateTime fecha = d.getItem().getFechaHoraAlta();
                    if (fecha == null) return true;
                    boolean dentroInicio = (fechaInicio == null || !fecha.isBefore(fechaInicio));
                    boolean dentroFin = (fechaFin == null || !fecha.isAfter(fechaFin));
                    return dentroInicio && dentroFin;
                })
                .map(d -> new InformeDonacionesDTO(
                        d.getItem().getCategoria(),
                        d.getItem().isEliminado(),
                        d.getCantidad()
                ))
                .toList();
    }


    public List<InformeDonacionesDTO> obtenerInformeAgrupado(
            CategoriaDonacion categoria,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Boolean eliminado) {

        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        List<DonacionesEvento> todas = donacionesEventoRepository.findAll();

        List<DonacionesEvento> filtradas = todas.stream()
                .filter(d -> d.getItem() != null)
                .filter(d -> categoria == null || d.getItem().getCategoria() == categoria)
                .filter(d -> eliminado == null || d.getItem().isEliminado() == eliminado)
                .filter(d -> {
                    LocalDateTime fecha = d.getItem().getFechaHoraAlta();
                    if (fecha == null) return true;
                    boolean dentroInicio = (fechaInicio == null || !fecha.isBefore(fechaInicio));
                    boolean dentroFin = (fechaFin == null || !fecha.isAfter(fechaFin));
                    return dentroInicio && dentroFin;
                })
                .toList();


        return filtradas.stream()
                .collect(Collectors.groupingBy(
                        d -> new AbstractMap.SimpleEntry<>(
                                d.getItem().getCategoria(),
                                d.getItem().isEliminado()),
                        Collectors.summingInt(DonacionesEvento::getCantidad)
                ))
                .entrySet().stream()
                .map(e -> new InformeDonacionesDTO(
                        e.getKey().getKey(),
                        e.getKey().getValue(),
                        e.getValue()
                ))
                .toList();
    }

}
