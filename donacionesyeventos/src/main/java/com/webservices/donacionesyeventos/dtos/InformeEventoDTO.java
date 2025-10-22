package com.webservices.donacionesyeventos.dtos;

import java.time.LocalDate;

public class InformeEventoDTO {

    private LocalDate dia;
    private String nombreEvento;
    private String descripcion;
    private int totalDonaciones;

    public InformeEventoDTO(LocalDate dia, String nombreEvento, String descripcion, int totalDonaciones) {
        this.dia = dia;
        this.nombreEvento = nombreEvento;
        this.descripcion = descripcion;
        this.totalDonaciones = totalDonaciones;
    }

    // Getters y setters
    public LocalDate getDia() { return dia; }
    public String getNombreEvento() { return nombreEvento; }
    public String getDescripcion() { return descripcion; }
    public int getTotalDonaciones() { return totalDonaciones; }

}
