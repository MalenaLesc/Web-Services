package com.webservices.donacionesyeventos.grapql;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Servicios.InformeDonacionesServicio;
import com.webservices.donacionesyeventos.dtos.InformeDonacionesDTO;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class InformeDonacionesController {

    private final InformeDonacionesServicio informeService;

    public InformeDonacionesController(InformeDonacionesServicio informeService) {
        this.informeService = informeService;
    }

    @QueryMapping
    public List<InformeDonacionesDTO> informeDonaciones(
            @Argument CategoriaDonacion categoria,
            @Argument LocalDateTime fechaInicio,
            @Argument LocalDateTime fechaFin,
            @Argument Boolean eliminado) {

        return informeService.obtenerInforme(categoria, fechaInicio, fechaFin, eliminado);
    }

    @QueryMapping
    public List<InformeDonacionesDTO> informeDonacionesAgrupado(
            @Argument CategoriaDonacion categoria,
            @Argument LocalDateTime fechaInicio,
            @Argument LocalDateTime fechaFin,
            @Argument Boolean eliminado) {

        return informeService.obtenerInformeAgrupado(categoria, fechaInicio, fechaFin, eliminado);
    }
}