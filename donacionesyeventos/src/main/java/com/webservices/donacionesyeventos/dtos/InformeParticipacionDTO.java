package com.webservices.donacionesyeventos.dtos;

import java.util.List;

public class InformeParticipacionDTO {
    private String mes;
    private List<InformeEventoDTO> eventos;

    public InformeParticipacionDTO(String mes, List<InformeEventoDTO> eventos) {
        this.mes = mes;
        this.eventos = eventos;
    }

    // Getters
    public String getMes() { return mes; }
    public List<InformeEventoDTO> getEventos() { return eventos; }
}
