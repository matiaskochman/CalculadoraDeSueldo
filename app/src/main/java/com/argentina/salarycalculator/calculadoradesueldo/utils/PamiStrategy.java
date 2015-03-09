package com.argentina.salarycalculator.calculadoradesueldo.utils;

/**
 * Created by Matias on 09/03/2015.
 */
public class PamiStrategy {

    private static final float PORCENTAJE_PAMI = 0.03f;

    public static float calcularPami(float sueldoBruto){
        return sueldoBruto*PORCENTAJE_PAMI;
    }
}
