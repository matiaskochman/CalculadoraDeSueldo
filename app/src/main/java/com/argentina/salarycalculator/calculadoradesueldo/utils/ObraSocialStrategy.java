package com.argentina.salarycalculator.calculadoradesueldo.utils;

/**
 * Created by Matias on 09/03/2015.
 */
public class ObraSocialStrategy {
    private static final float PORCENTAJE_OBRA_SOCIAL= 0.03f;

    public static float calcularObraSocial(float sueldoBruto){
        return sueldoBruto*PORCENTAJE_OBRA_SOCIAL;
    }
}
