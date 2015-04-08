package com.argentina.salarycalculator.calculadoradesueldo.utils;

import com.argentina.salarycalculator.calculadoradesueldo.model.OpcionMensualEscalaImpuesto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Matias on 14/03/2015.
 */
public class CalculadoraSueldo {

    public static Map<String,Float[]> calcular(Float salarioBrutoMensualOriginal,List listaGastos,String porcentajeSindicato,
                                               Map<String,Integer> variables){

        Map<String,Float[]> result = new HashMap<String, Float[]>();

        Map<Float, OpcionMensualEscalaImpuesto>[] tabla_retenciones_mensuales = inicializar_tabla();

        Float impuesto_anual_a_pagar = 0f;


        Float[] imputable_mes = {0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};
        Float[] a_pagar_mes = {0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};
        Float[] acumulado_mes = {0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f};

        Float salarioBrutoMensual = 0f;

        for(int mes=1;mes<=12;mes++){

            /**
             * deshabilitado porque serviria para calcular ganancias con aguinaldo, anual.
             * y quiero calcular mensual
             */
            if(mes==6 || mes==12){
                salarioBrutoMensual = salarioBrutoMensualOriginal + salarioBrutoMensualOriginal/2;
            }else{
                salarioBrutoMensual = salarioBrutoMensualOriginal;
            }
            /*
            salarioBrutoMensual = salarioBrutoMensualOriginal;
            */

            Float gananciaNetaAcumuladaMensual = 0f;


            gananciaNetaAcumuladaMensual = salarioBrutoMensual;

            imputable_mes[mes] = calculoAnteriorAGanancias(gananciaNetaAcumuladaMensual,listaGastos,variables) + imputable_mes[mes-1];

            a_pagar_mes[mes] =  calcularMes(tabla_retenciones_mensuales[mes-1], tabla_retenciones_mensuales[mes-1].keySet(), imputable_mes[mes]) - acumulado_mes[mes-1];
            acumulado_mes[mes] =a_pagar_mes[mes]+acumulado_mes[mes-1];

            impuesto_anual_a_pagar +=a_pagar_mes[mes];
        }

        result.put("a_pagar_mes", a_pagar_mes);
        result.put("imputable_mes", imputable_mes);


        return result;


    }

    private static Float calculoAnteriorAGanancias(Float gananciaNetaAcumuladaMensual, List<Float> listaGastos,Map<String,Integer> variables) {
        float GANANCIA_NO_IMPONIBLE = 18662f;
        float GANANCIA_NO_IMPONIBLE_ASALARIADO = 89580f;
        float CASADO = 20736f;
        float CADA_HIJO = 10368;
        float CADA_PARIENTE = 7776f;



        Float imputable = gananciaNetaAcumuladaMensual
                - GANANCIA_NO_IMPONIBLE/12
                - GANANCIA_NO_IMPONIBLE_ASALARIADO/12;
        /*
                - CADA_HIJO/12
                - CASADO/12;*/
        if(variables.get("casado")>0){
            imputable = imputable - CASADO/12;
        }
        if(variables.get("hijos")>0){
            imputable = imputable - (variables.get("hijos")*CADA_HIJO/12);
        }
        if(variables.get("parientes")>0){
            imputable = imputable - (variables.get("parientes")*CADA_PARIENTE/12);
        }



        if(listaGastos!=null && !listaGastos.isEmpty()){

            for (Float gasto : listaGastos) {
                imputable -=gasto;
            }
        }

        return imputable;
    }

    private static Map<Float, OpcionMensualEscalaImpuesto>[] inicializar_tabla() {
        Map<Float,OpcionMensualEscalaImpuesto>[] tabla_retenciones_mensuales = new TreeMap[12];

        Map<Float,OpcionMensualEscalaImpuesto> tabla_enero = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        Float topeSalario = 1000000000f;

        OpcionMensualEscalaImpuesto a1 = new OpcionMensualEscalaImpuesto(0f, 833.33f,0f,0.09f);
        OpcionMensualEscalaImpuesto b1 = new OpcionMensualEscalaImpuesto(833.33f, 1666.67f,75f,0.14f);
        OpcionMensualEscalaImpuesto c1 = new OpcionMensualEscalaImpuesto(1666.67f,2500f,191.67f,0.19f);
        OpcionMensualEscalaImpuesto d1 = new OpcionMensualEscalaImpuesto(2500f, 5000f,350f,0.23f);
        OpcionMensualEscalaImpuesto e1 = new OpcionMensualEscalaImpuesto(5000f, 7500f,925f,0.27f);
        OpcionMensualEscalaImpuesto f1 = new OpcionMensualEscalaImpuesto(7500f, 10000f,1600f,0.31f);
        OpcionMensualEscalaImpuesto g1 = new OpcionMensualEscalaImpuesto(10000f, topeSalario,2375f,0.35f);


        tabla_enero.put(0f,a1);
        tabla_enero.put(833.33f,b1);
        tabla_enero.put(1666.67f,c1);
        tabla_enero.put(2500f,d1);
        tabla_enero.put(5000f,e1);
        tabla_enero.put(7500f,f1);
        tabla_enero.put(10000f,g1);

        tabla_retenciones_mensuales[0] = tabla_enero;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_febrero = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a2 = new OpcionMensualEscalaImpuesto(0f, 1666.67f,0f,0.09f);
        OpcionMensualEscalaImpuesto b2 = new OpcionMensualEscalaImpuesto(1666.67f, 3333.33f,150f,0.14f);
        OpcionMensualEscalaImpuesto c2 = new OpcionMensualEscalaImpuesto(3333.33f,5000f,383.33f,0.19f);
        OpcionMensualEscalaImpuesto d2 = new OpcionMensualEscalaImpuesto(5000f, 10000f,700f,0.23f);
        OpcionMensualEscalaImpuesto e2 = new OpcionMensualEscalaImpuesto(10000f, 15000f,1850f,0.27f);
        OpcionMensualEscalaImpuesto f2 = new OpcionMensualEscalaImpuesto(15000f, 20000f,3200f,0.31f);
        OpcionMensualEscalaImpuesto g2 = new OpcionMensualEscalaImpuesto(20000f, topeSalario,4750f,0.35f);


        tabla_febrero.put(0f,a2);
        tabla_febrero.put(1666.67f,b2);
        tabla_febrero.put(3333.33f,c2);
        tabla_febrero.put(5000f,d2);
        tabla_febrero.put(10000f,e2);
        tabla_febrero.put(15000f,f2);
        tabla_febrero.put(20000f,g2);

        tabla_retenciones_mensuales[1] = tabla_febrero;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_marzo = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a3 = new OpcionMensualEscalaImpuesto(0f, 2500f,0f,0.09f);
        OpcionMensualEscalaImpuesto b3 = new OpcionMensualEscalaImpuesto(2500f, 5000f,225f,0.14f);
        OpcionMensualEscalaImpuesto c3 = new OpcionMensualEscalaImpuesto(5000f,7500f,575f,0.19f);
        OpcionMensualEscalaImpuesto d3 = new OpcionMensualEscalaImpuesto(7500f, 15000f,1050f,0.23f);
        OpcionMensualEscalaImpuesto e3 = new OpcionMensualEscalaImpuesto(15000f, 22500f,2775f,0.27f);
        OpcionMensualEscalaImpuesto f3 = new OpcionMensualEscalaImpuesto(22500f, 30000f,4800f,0.31f);
        OpcionMensualEscalaImpuesto g3 = new OpcionMensualEscalaImpuesto(30000f, topeSalario,7125f,0.35f);


        tabla_marzo.put(0f,a3);
        tabla_marzo.put(1666.67f,b3);
        tabla_marzo.put(3333.33f,c3);
        tabla_marzo.put(5000f,d3);
        tabla_marzo.put(10000f,e3);
        tabla_marzo.put(15000f,f3);
        tabla_marzo.put(20000f,g3);

        tabla_retenciones_mensuales[2] = tabla_marzo;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_abril = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a4 = new OpcionMensualEscalaImpuesto(0f, 3333.33f,0f,0.09f);
        OpcionMensualEscalaImpuesto b4 = new OpcionMensualEscalaImpuesto(3333.33f, 6666.67f,300f,0.14f);
        OpcionMensualEscalaImpuesto c4 = new OpcionMensualEscalaImpuesto(6666.67f,10000f,766.67f,0.19f);
        OpcionMensualEscalaImpuesto d4 = new OpcionMensualEscalaImpuesto(10000f, 20000f,1400f,0.23f);
        OpcionMensualEscalaImpuesto e4 = new OpcionMensualEscalaImpuesto(20000f, 30000f,3700f,0.27f);
        OpcionMensualEscalaImpuesto f4 = new OpcionMensualEscalaImpuesto(30000f, 40000f,6400f,0.31f);
        OpcionMensualEscalaImpuesto g4 = new OpcionMensualEscalaImpuesto(40000f, topeSalario,9500f,0.35f);


        tabla_abril.put(0f,a4);
        tabla_abril.put(3333.33f,b4);
        tabla_abril.put(6666.67f,c4);
        tabla_abril.put(10000f,d4);
        tabla_abril.put(20000f,e4);
        tabla_abril.put(30000f,f4);
        tabla_abril.put(40000f,g4);

        tabla_retenciones_mensuales[3] = tabla_abril;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_mayo = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a5 = new OpcionMensualEscalaImpuesto(0f, 4666.67f,0f,0.09f);
        OpcionMensualEscalaImpuesto b5 = new OpcionMensualEscalaImpuesto(4666.67f, 8333.33f,375f,0.14f);
        OpcionMensualEscalaImpuesto c5 = new OpcionMensualEscalaImpuesto(8333.33f,12500f,958.33f,0.19f);
        OpcionMensualEscalaImpuesto d5 = new OpcionMensualEscalaImpuesto(12500f, 25000f,1750f,0.23f);
        OpcionMensualEscalaImpuesto e5 = new OpcionMensualEscalaImpuesto(25000f, 37500f,4625f,0.27f);
        OpcionMensualEscalaImpuesto f5 = new OpcionMensualEscalaImpuesto(37500f, 50000f,8000f,0.31f);
        OpcionMensualEscalaImpuesto g5 = new OpcionMensualEscalaImpuesto(50000f, topeSalario,11875f,0.35f);


        tabla_mayo.put(0f,a5);
        tabla_mayo.put(4666.67f,b5);
        tabla_mayo.put(8333.33f,c5);
        tabla_mayo.put(12500f,d5);
        tabla_mayo.put(25000f,e5);
        tabla_mayo.put(37500f,f5);
        tabla_mayo.put(50000f,g5);

        tabla_retenciones_mensuales[4] = tabla_mayo;


        Map<Float,OpcionMensualEscalaImpuesto> tabla_junio = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a6 = new OpcionMensualEscalaImpuesto(0f, 5000f,0f,0.09f);
        OpcionMensualEscalaImpuesto b6 = new OpcionMensualEscalaImpuesto(5000f, 10000f,450f,0.14f);
        OpcionMensualEscalaImpuesto c6 = new OpcionMensualEscalaImpuesto(10000f,15000f,1150f,0.19f);
        OpcionMensualEscalaImpuesto d6 = new OpcionMensualEscalaImpuesto(15000f, 30000f,2100f,0.23f);
        OpcionMensualEscalaImpuesto e6 = new OpcionMensualEscalaImpuesto(30000f, 45000f,5550f,0.27f);
        OpcionMensualEscalaImpuesto f6 = new OpcionMensualEscalaImpuesto(45000f, 60000f,9600f,0.31f);
        OpcionMensualEscalaImpuesto g6 = new OpcionMensualEscalaImpuesto(60000f, topeSalario,14250f,0.35f);


        tabla_junio.put(0f,a6);
        tabla_junio.put(5000f,b6);
        tabla_junio.put(10000f,c6);
        tabla_junio.put(15000f,d6);
        tabla_junio.put(30000f,e6);
        tabla_junio.put(45000f,f6);
        tabla_junio.put(60000f,g6);

        tabla_retenciones_mensuales[5] = tabla_junio;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_julio = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a7 = new OpcionMensualEscalaImpuesto(0f, 5833.33f,0f,0.09f);
        OpcionMensualEscalaImpuesto b7 = new OpcionMensualEscalaImpuesto(5833.33f, 11666.67f,525f,0.14f);
        OpcionMensualEscalaImpuesto c7 = new OpcionMensualEscalaImpuesto(11666.67f,17500f,1341.67f,0.19f);
        OpcionMensualEscalaImpuesto d7 = new OpcionMensualEscalaImpuesto(17500f, 35000f,2450f,0.23f);
        OpcionMensualEscalaImpuesto e7 = new OpcionMensualEscalaImpuesto(35000f, 52500f,6475f,0.27f);
        OpcionMensualEscalaImpuesto f7 = new OpcionMensualEscalaImpuesto(52500f, 70000f,11200f,0.31f);
        OpcionMensualEscalaImpuesto g7 = new OpcionMensualEscalaImpuesto(70000f, topeSalario,16625f,0.35f);


        tabla_julio.put(0f,a7);
        tabla_julio.put(5833.33f,b7);
        tabla_julio.put(11666.67f,c7);
        tabla_julio.put(17500f,d7);
        tabla_julio.put(35000f,e7);
        tabla_julio.put(525000f,f7);
        tabla_julio.put(70000f,g7);

        tabla_retenciones_mensuales[6] = tabla_julio;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_agosto = new TreeMap<Float,OpcionMensualEscalaImpuesto>();

        OpcionMensualEscalaImpuesto a8 = new OpcionMensualEscalaImpuesto(0f, 6666.67f,0f,0.09f);
        OpcionMensualEscalaImpuesto b8 = new OpcionMensualEscalaImpuesto(6666.67f, 13333.33f,600f,0.14f);
        OpcionMensualEscalaImpuesto c8 = new OpcionMensualEscalaImpuesto(13333.33f,20000f,1533.33f,0.19f);
        OpcionMensualEscalaImpuesto d8 = new OpcionMensualEscalaImpuesto(20000f, 40000f,2800f,0.23f);
        OpcionMensualEscalaImpuesto e8 = new OpcionMensualEscalaImpuesto(40000f, 60000f,7400f,0.27f);
        OpcionMensualEscalaImpuesto f8 = new OpcionMensualEscalaImpuesto(60000f, 80000f,12800f,0.31f);
        OpcionMensualEscalaImpuesto g8 = new OpcionMensualEscalaImpuesto(80000f, topeSalario,19000f,0.35f);


        tabla_agosto.put(0f,a8);
        tabla_agosto.put(6666.67f,b8);
        tabla_agosto.put(13333.33f,c8);
        tabla_agosto.put(20000f,d8);
        tabla_agosto.put(40000f,e8);
        tabla_agosto.put(60000f,f8);
        tabla_agosto.put(80000f,g8);

        tabla_retenciones_mensuales[7] = tabla_agosto;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_septiembre = new TreeMap<Float,OpcionMensualEscalaImpuesto>();


        Float a = 0f;
        Float b = 7500f;
        Float c = 15000f;
        Float d = 22500f;
        Float e = 45000f;
        Float f = 67500f;
        Float g = 90000f;
        Float h = topeSalario;

        OpcionMensualEscalaImpuesto a9 = new OpcionMensualEscalaImpuesto(a, b,0f,0.09f);
        OpcionMensualEscalaImpuesto b9 = new OpcionMensualEscalaImpuesto(b, c,675f,0.14f);
        OpcionMensualEscalaImpuesto c9 = new OpcionMensualEscalaImpuesto(c, d,1725f,0.19f);
        OpcionMensualEscalaImpuesto d9 = new OpcionMensualEscalaImpuesto(d, e,3150f,0.23f);
        OpcionMensualEscalaImpuesto e9 = new OpcionMensualEscalaImpuesto(e, f,8325f,0.27f);
        OpcionMensualEscalaImpuesto f9 = new OpcionMensualEscalaImpuesto(f, g,14400f,0.31f);
        OpcionMensualEscalaImpuesto g9 = new OpcionMensualEscalaImpuesto(g, h,21375f,0.35f);


        tabla_septiembre.put(a,a9);
        tabla_septiembre.put(b,b9);
        tabla_septiembre.put(c,c9);
        tabla_septiembre.put(d,d9);
        tabla_septiembre.put(e,e9);
        tabla_septiembre.put(f,f9);
        tabla_septiembre.put(g,g9);

        tabla_retenciones_mensuales[8] = tabla_septiembre;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_octubre = new TreeMap<Float,OpcionMensualEscalaImpuesto>();


        a = 0f;
        b = 8333.33f;
        c = 16666.67f;
        d = 25000f;
        e = 50000f;
        f = 75000f;
        g = 100000f;
        h = topeSalario;

        OpcionMensualEscalaImpuesto a10 = new OpcionMensualEscalaImpuesto(a, b,0f,0.09f);
        OpcionMensualEscalaImpuesto b10 = new OpcionMensualEscalaImpuesto(b, c,750f,0.14f);
        OpcionMensualEscalaImpuesto c10 = new OpcionMensualEscalaImpuesto(c, d,1916.67f,0.19f);
        OpcionMensualEscalaImpuesto d10 = new OpcionMensualEscalaImpuesto(d, e,3500f,0.23f);
        OpcionMensualEscalaImpuesto e10 = new OpcionMensualEscalaImpuesto(e, f,9250f,0.27f);
        OpcionMensualEscalaImpuesto f10 = new OpcionMensualEscalaImpuesto(f, g,16000f,0.31f);
        OpcionMensualEscalaImpuesto g10 = new OpcionMensualEscalaImpuesto(g, h,23750f,0.35f);


        tabla_octubre.put(a,a10);
        tabla_octubre.put(b,b10);
        tabla_octubre.put(c,c10);
        tabla_octubre.put(d,d10);
        tabla_octubre.put(e,e10);
        tabla_octubre.put(f,f10);
        tabla_octubre.put(g,g10);

        tabla_retenciones_mensuales[9] = tabla_octubre;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_noviembre = new TreeMap<Float,OpcionMensualEscalaImpuesto>();


        a = 0f;
        b = 9166.67f;
        c = 18333.33f;
        d = 27500f;
        e = 55000f;
        f = 82500f;
        g = 110000f;
        h = topeSalario;

        OpcionMensualEscalaImpuesto a11 = new OpcionMensualEscalaImpuesto(a, b,0f,0.09f);
        OpcionMensualEscalaImpuesto b11 = new OpcionMensualEscalaImpuesto(b, c,825f,0.14f);
        OpcionMensualEscalaImpuesto c11 = new OpcionMensualEscalaImpuesto(c, d,2108.33f,0.19f);
        OpcionMensualEscalaImpuesto d11 = new OpcionMensualEscalaImpuesto(d, e,3850f,0.23f);
        OpcionMensualEscalaImpuesto e11 = new OpcionMensualEscalaImpuesto(e, f,10175f,0.27f);
        OpcionMensualEscalaImpuesto f11 = new OpcionMensualEscalaImpuesto(f, g,17600f,0.31f);
        OpcionMensualEscalaImpuesto g11 = new OpcionMensualEscalaImpuesto(g, h,26125f,0.35f);


        tabla_noviembre.put(a,a11);
        tabla_noviembre.put(b,b11);
        tabla_noviembre.put(c,c11);
        tabla_noviembre.put(d,d11);
        tabla_noviembre.put(e,e11);
        tabla_noviembre.put(f,f11);
        tabla_noviembre.put(g,g11);

        tabla_retenciones_mensuales[10] = tabla_noviembre;

        Map<Float,OpcionMensualEscalaImpuesto> tabla_diciembre = new TreeMap<Float,OpcionMensualEscalaImpuesto>();


        a = 0f;
        b = 10000f;
        c = 20000f;
        d = 30000f;
        e = 60000f;
        f = 90000f;
        g = 120000f;
        h = topeSalario;

        OpcionMensualEscalaImpuesto a12 = new OpcionMensualEscalaImpuesto(a, b,0f,0.09f);
        OpcionMensualEscalaImpuesto b12 = new OpcionMensualEscalaImpuesto(b, c,900f,0.14f);
        OpcionMensualEscalaImpuesto c12 = new OpcionMensualEscalaImpuesto(c, d,2300f,0.19f);
        OpcionMensualEscalaImpuesto d12 = new OpcionMensualEscalaImpuesto(d, e,4200f,0.23f);
        OpcionMensualEscalaImpuesto e12 = new OpcionMensualEscalaImpuesto(e, f,11100f,0.27f);
        OpcionMensualEscalaImpuesto f12 = new OpcionMensualEscalaImpuesto(f, g,19200f,0.31f);
        OpcionMensualEscalaImpuesto g12 = new OpcionMensualEscalaImpuesto(g, h,28500f,0.35f);


        tabla_diciembre.put(a,a12);
        tabla_diciembre.put(b,b12);
        tabla_diciembre.put(c,c12);
        tabla_diciembre.put(d,d12);
        tabla_diciembre.put(e,e12);
        tabla_diciembre.put(f,f12);
        tabla_diciembre.put(g,g12);

        tabla_retenciones_mensuales[11] = tabla_diciembre;
        return tabla_retenciones_mensuales;
    }

    private static Float calcularMes(Map<Float, OpcionMensualEscalaImpuesto> tabla_retenciones_mensuales,
                              Set<Float> keys, Float imputable) {

        Float impuestoCalculado = 0f;
        Float impuestoAcumulado =0f;
        int contador=0;
        float impuesto_imputable = imputable;
        for ( Float key : keys ) {
            contador++;

            Float desde = tabla_retenciones_mensuales.get(key).getValorDesde();
            Float hasta = tabla_retenciones_mensuales.get(key).getValorHasta();

            if(imputable<hasta && imputable>=desde){


                switch(contador){
                    case 1:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 2:
        			/*
        			 * crear otro map y meterlo en tabla_retenciones array de array de maps;
        			 */
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-= tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 3:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-=tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 4:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-=tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 5:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-=tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 6:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-=tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;
                    case 7:
                        impuestoAcumulado += tabla_retenciones_mensuales.get(key).getImporteAcumulado();
                        impuesto_imputable-=tabla_retenciones_mensuales.get(key).getPagarSobreExcedente();
                        impuestoCalculado = impuesto_imputable * tabla_retenciones_mensuales.get(key).getPorcentaje();

                        break;

                    default:

                }

                break;
            }else{

                continue;
            }

        }
        float impuesto_a_pagar = impuestoAcumulado+impuestoCalculado;
        return impuesto_a_pagar;
    }




    public float calcularJubilacion(float sueldoBruto){

        float PORCENTAJE_JUBILACION= 0.11f;
        return sueldoBruto*PORCENTAJE_JUBILACION;
    }


    public float calcularPami(float sueldoBruto){
        float PORCENTAJE_PAMI = 0.03f;
        return sueldoBruto*PORCENTAJE_PAMI;
    }


    public float calcularObraSocial(float sueldoBruto){
        float PORCENTAJE_OBRA_SOCIAL= 0.03f;
        return sueldoBruto*PORCENTAJE_OBRA_SOCIAL;
    }

    public float calcularSindicato(float salarioBruto, String porcentajeSindicato){

        float sindicatoValor = 0f;
        if(porcentajeSindicato!=null && !porcentajeSindicato.equals("")){
            sindicatoValor = Float.valueOf(porcentajeSindicato);
        }

        float sindicatoPorcentaje = sindicatoValor/100f;
        Float totalSindicato = salarioBruto*sindicatoPorcentaje;
        return totalSindicato;
    }
}
