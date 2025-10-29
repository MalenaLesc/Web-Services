package com.webservices.donacionesyeventos.Servicios;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webservices.donacionesyeventos.Entidades.DonacionesEvento;
import com.webservices.donacionesyeventos.Entidades.EventoSolidario;
import com.webservices.donacionesyeventos.Entidades.Usuario;
import com.webservices.donacionesyeventos.Repositorios.DonacionesEventoRepository;
import com.webservices.donacionesyeventos.Repositorios.EventoRepository;
import com.webservices.donacionesyeventos.Repositorios.UsuarioRepository;
import com.webservices.donacionesyeventos.dtos.InformeEventoDTO;
import com.webservices.donacionesyeventos.dtos.InformeParticipacionDTO;

@Service
public class InformeParticipacionServicio {

        @Autowired
        private EventoRepository eventoRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private DonacionesEventoRepository donacionesRepository;

        public List<InformeParticipacionDTO> obtenerInforme(
                        Long usuarioId,
                        LocalDateTime fechaInicio,
                        LocalDateTime fechaFin) {

                Usuario usuario = usuarioRepository.findById(usuarioId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Filtrar solo los eventos donde el usuario participó
                List<EventoSolidario> eventos = eventoRepository.findAll().stream()
                                .filter(e -> e.getParticipantesEvento().contains(usuario))
                                .filter(e -> fechaInicio == null || !e.getFechaHoraEvento().isBefore(fechaInicio))
                                .filter(e -> fechaFin == null || !e.getFechaHoraEvento().isAfter(fechaFin))
                                .toList();

                // Agrupar por mes
                Map<String, List<InformeEventoDTO>> agrupadoPorMes = new TreeMap<>();

                for (EventoSolidario evento : eventos) {
                        // Calcular total de donaciones en ese evento
                        int totalDonaciones = donacionesRepository.findByEvento(evento).stream()
                                        .mapToInt(DonacionesEvento::getCantidad)
                                        .sum();

                        String mes = evento.getFechaHoraEvento()
                                        .getMonth()
                                        .getDisplayName(TextStyle.FULL, new Locale("es", "ES"));

                        agrupadoPorMes
                                        .computeIfAbsent(mes, k -> new ArrayList<>())
                                        .add(new InformeEventoDTO(
                                                        evento.getFechaHoraEvento().toLocalDate(),
                                                        evento.getNombreEvento(),
                                                        evento.getDescripcion(),
                                                        totalDonaciones));
                }

                return agrupadoPorMes.entrySet().stream()
                                .map(entry -> new InformeParticipacionDTO(entry.getKey(), entry.getValue()))
                                .toList();
        }
}
