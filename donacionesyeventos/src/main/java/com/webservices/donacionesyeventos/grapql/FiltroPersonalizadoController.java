package com.webservices.donacionesyeventos.grapql;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Entidades.FiltroPersonalizado;
import com.webservices.donacionesyeventos.Servicios.FiltroPersonalizadoServicio;



@Controller
public class FiltroPersonalizadoController  {
    
    private final FiltroPersonalizadoServicio servicio;

    public FiltroPersonalizadoController(FiltroPersonalizadoServicio servicio) {
        this.servicio = servicio;
    }

    @QueryMapping
    public List<FiltroPersonalizado> filtrosPorUsuario(@Argument Long usuarioId) {
        return servicio.obtenerFiltrosPorUsuario(usuarioId);
    }

    @MutationMapping
    public FiltroPersonalizado guardarFiltro(
            @Argument Long usuarioId,
            @Argument String nombreFiltro,
            @Argument CategoriaDonacion categoria,
            @Argument LocalDateTime fechaInicio,
            @Argument LocalDateTime fechaFin,
            @Argument Boolean eliminado) {
        return servicio.guardarFiltro(usuarioId, nombreFiltro, categoria, fechaInicio, fechaFin, eliminado);
    }

    @MutationMapping
    public Boolean eliminarFiltro(@Argument Long id) {
        return servicio.eliminarFiltro(id);
    }

    @MutationMapping
    public FiltroPersonalizado editarFiltro(
        @Argument Long id,
        @Argument String nombreFiltro,
        @Argument CategoriaDonacion categoria,
        @Argument LocalDateTime fechaInicio,
        @Argument LocalDateTime fechaFin,
        @Argument Boolean eliminado) {
    return servicio.editarFiltro(id, nombreFiltro, categoria, fechaInicio, fechaFin, eliminado);
}


}