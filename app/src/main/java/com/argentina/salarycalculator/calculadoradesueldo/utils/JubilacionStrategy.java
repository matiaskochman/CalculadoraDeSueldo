package com.argentina.salarycalculator.calculadoradesueldo.utils;

/**
 * Created by Matias on 09/03/2015.
 */
public class JubilacionStrategy {
    private static final float PORCENTAJE_JUBILACION= 0.11f;

    public static float calcularJubilacion(float sueldoBruto){
        return sueldoBruto*PORCENTAJE_JUBILACION;
    }
}
