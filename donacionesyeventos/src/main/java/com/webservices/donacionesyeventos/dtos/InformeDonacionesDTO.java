package com.webservices.donacionesyeventos.dtos;

import com.webservices.donacionesyeventos.Entidades.CategoriaDonacion;

public class InformeDonacionesDTO {

    private CategoriaDonacion categoria;
    private boolean eliminado;
    private int total;

    public InformeDonacionesDTO(CategoriaDonacion categoria, boolean eliminado, int total) {
        this.categoria = categoria;
        this.eliminado = eliminado;
        this.total = total;
    }

    public CategoriaDonacion getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaDonacion categoria) {
        this.categoria = categoria;
    }
    public boolean isEliminado() {
        return eliminado;
    }
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    
    
}
