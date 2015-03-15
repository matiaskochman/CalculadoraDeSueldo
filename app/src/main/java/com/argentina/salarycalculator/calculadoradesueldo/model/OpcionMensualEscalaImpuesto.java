package com.argentina.salarycalculator.calculadoradesueldo.model;

/**
 * Created by Matias on 14/03/2015.
 */
public class OpcionMensualEscalaImpuesto {
    private Float valorDesde;
    private Float valorHasta;
    private Float importeAcumulado;
    private Float porcentaje;
    private Float pagarSobreExcedente;


    public OpcionMensualEscalaImpuesto(Float valorDesde, Float valorHasta,
                                       Float importeAcumulado, Float porcentaje) {
        super();
        this.valorDesde = valorDesde;
        this.valorHasta = valorHasta;
        this.importeAcumulado = importeAcumulado;
        this.porcentaje = porcentaje;
        this.pagarSobreExcedente = valorDesde;
    }

    public Float getValorDesde() {
        return valorDesde;
    }
    public void setValorDesde(Float valorDesde) {
        this.valorDesde = valorDesde;
    }
    public Float getValorHasta() {
        return valorHasta;
    }
    public void setValorHasta(Float valorHasta) {
        this.valorHasta = valorHasta;
    }
    public Float getImporteAcumulado() {
        return importeAcumulado;
    }
    public void setImporteAcumulado(Float importeAcumulado) {
        this.importeAcumulado = importeAcumulado;
    }
    public Float getPorcentaje() {
        return porcentaje;
    }
    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }
    public Float getPagarSobreExcedente() {
        return pagarSobreExcedente;
    }
    public void setPagarSobreExcedente(Float pagarSobreExcedente) {
        this.pagarSobreExcedente = pagarSobreExcedente;
    }
}
