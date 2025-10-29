package com.webservices.donacionesyeventos.Entidades;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "filtros_personalizados")
public class FiltroPersonalizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreFiltro;

    @Enumerated(EnumType.STRING)
    private CategoriaDonacion categoria;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    private Boolean eliminado; 

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombreFiltro() {
        return nombreFiltro;
    }
    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }
    public CategoriaDonacion getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDonacion categoria) {
        this.categoria = categoria; 
    }
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
    public Boolean getEliminado() {
        return eliminado;
    }
    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FiltroPersonalizado(Long id, String nombreFiltro, CategoriaDonacion categoria, LocalDateTime fechaInicio,
            LocalDateTime fechaFin, Boolean eliminado, Usuario usuario) {
        this.id = id;
        this.nombreFiltro = nombreFiltro;
        this.categoria = categoria;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.eliminado = eliminado;
        this.usuario = usuario;
    }
    public FiltroPersonalizado() {
        
    }


}
