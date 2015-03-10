package com.argentina.salarycalculator.calculadoradesueldo.utils;

/**
 * Created by Matias on 09/03/2015.
 */
public class GananciasStrategy {

    private static final float GANANCIA_NO_IMPONIBLE = 18662f;
    private static final float GANANCIA_NO_IMPONIBLE_ASALARIADO = 89580f;
    private static final float CASADO = 20736f;
    private static final float CADA_HIJO = 10368;
    private static final float CADA_PARIENTE = 7776f;

    public static float calcularGanancia(float gananciaImponibleMensual,boolean casado , int hijos, int parientes){

        float acumuladoSueldoBrutoAnual = gananciaImponibleMensual*13f;

        float ganancia_imponible = acumuladoSueldoBrutoAnual - GANANCIA_NO_IMPONIBLE
                -GANANCIA_NO_IMPONIBLE_ASALARIADO;

        float gananciaAPagarAnual = 0f;

        if(casado){
            ganancia_imponible = ganancia_imponible - CASADO;
        }
        if(hijos>0){
            ganancia_imponible = ganancia_imponible - (hijos*CADA_HIJO);
        }
        if(parientes>0){
            ganancia_imponible = ganancia_imponible - (hijos*CADA_PARIENTE);
        }


        if(ganancia_imponible<=10000f && ganancia_imponible>0f){
            gananciaAPagarAnual = ganancia_imponible*0.09f;
        }else if(ganancia_imponible<=20000f){
            gananciaAPagarAnual = 900f+ (ganancia_imponible-10000f)*0.14f;
        }else if(ganancia_imponible<=30000f){
            gananciaAPagarAnual = 2300f+ (ganancia_imponible-20000f)*0.19f;
        }else if(ganancia_imponible<=60000f){
            gananciaAPagarAnual = 4200f+ (ganancia_imponible-30000f)*0.23f;
        }else if(ganancia_imponible<=90000f){
            gananciaAPagarAnual = 11100f+ (ganancia_imponible-60000f)*0.27f;
        }else if(ganancia_imponible<=120000f){
            gananciaAPagarAnual = 19200f+ (ganancia_imponible-90000f)*0.31f;
        }else if(ganancia_imponible>120000f){
            gananciaAPagarAnual = 28500f+ (ganancia_imponible-120000f)*0.35f;
        }else if(ganancia_imponible<0){
            gananciaAPagarAnual=0f;
        }

        Float gananciaAPagarMensual = gananciaAPagarAnual/12;

        return gananciaAPagarMensual;
    }
}
