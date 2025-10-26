package com.webservices.donacionesyeventos.Servicios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;
import com.webservices.donacionesyeventos.Entidades.FiltroPersonalizado;
import com.webservices.donacionesyeventos.Entidades.Usuario;
import com.webservices.donacionesyeventos.Repositorios.FiltroPersonalizadoRepository;

@Service
public class FiltroPersonalizadoServicio {
    @Autowired
    private FiltroPersonalizadoRepository filtroRepository;

    @Autowired
    private UsuarioServicio usuarioServicio; // para obtener el Usuario desde el ID

    public FiltroPersonalizado guardarFiltro(Long usuarioId, String nombreFiltro,
                                             CategoriaDonacion categoria,
                                             LocalDateTime fechaInicio,
                                             LocalDateTime fechaFin,
                                             Boolean eliminado) {

        Usuario usuario = usuarioServicio.getUsuarioById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + usuarioId);
        }

        FiltroPersonalizado filtro = new FiltroPersonalizado();
        filtro.setUsuario(usuario);
        filtro.setNombreFiltro(nombreFiltro);
        filtro.setCategoria(categoria);
        filtro.setFechaInicio(fechaInicio);
        filtro.setFechaFin(fechaFin);
        filtro.setEliminado(eliminado);

        return filtroRepository.save(filtro);
    }



    public List<FiltroPersonalizado> obtenerFiltrosPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioServicio.getUsuarioById(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + usuarioId);
        }
        return filtroRepository.findByUsuario(usuario);
    }

 
    public FiltroPersonalizado getFiltro(Long id) {
        return filtroRepository.findById(id).orElse(null);
    }

    public boolean eliminarFiltro(Long id) {
        if (filtroRepository.existsById(id)) {
            filtroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public FiltroPersonalizado editarFiltro(Long id, String nombreFiltro,
                                        CategoriaDonacion categoria,
                                        LocalDateTime fechaInicio,
                                        LocalDateTime fechaFin,
                                        Boolean eliminado) {
    return filtroRepository.findById(id)
            .map(filtro -> {
                if (nombreFiltro != null) filtro.setNombreFiltro(nombreFiltro);
                if (categoria != null) filtro.setCategoria(categoria);
                if (fechaInicio != null) filtro.setFechaInicio(fechaInicio);
                if (fechaFin != null) filtro.setFechaFin(fechaFin);
                if (eliminado != null) filtro.setEliminado(eliminado);
                return filtroRepository.save(filtro);
            })
            .orElseThrow(() -> new RuntimeException("Filtro no encontrado con id " + id));
}

}



