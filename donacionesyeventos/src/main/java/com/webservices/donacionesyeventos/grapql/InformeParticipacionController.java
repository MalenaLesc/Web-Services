package com.webservices.donacionesyeventos.grapql;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.webservices.donacionesyeventos.Servicios.InformeParticipacionServicio;
import com.webservices.donacionesyeventos.dtos.InformeParticipacionDTO;

@Controller
public class InformeParticipacionController {

    private final InformeParticipacionServicio servicio;

    public InformeParticipacionController(InformeParticipacionServicio servicio) {
        this.servicio = servicio;
    }

    @QueryMapping
    public List<InformeParticipacionDTO> informeParticipacion(
            @Argument Long usuarioId,
            @Argument LocalDateTime fechaInicio,
            @Argument LocalDateTime fechaFin) {
        return servicio.obtenerInforme(usuarioId, fechaInicio, fechaFin);
    }
}
