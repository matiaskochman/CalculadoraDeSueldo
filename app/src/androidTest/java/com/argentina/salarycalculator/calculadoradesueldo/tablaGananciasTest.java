package com.argentina.salarycalculator.calculadoradesueldo;

import android.test.suitebuilder.annotation.SmallTest;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Matias on 12/03/2015.
 */
public class tablaGananciasTest {

    @SmallTest
    public void test(){


        Map<Float,Float>[] tabla_retenciones_mensuales = new TreeMap[12];

        Map<Float,Float> tabla_enero = new TreeMap<Float,Float>();

        tabla_enero.put(833.33f,0.09f);
        tabla_enero.put(1666.67f,0.14f);
        tabla_enero.put(2500f,0.19f);
        tabla_enero.put(5000f,0.23f);
        tabla_enero.put(7500f,0.27f);
        tabla_enero.put(10000f,0.31f);

        tabla_retenciones_mensuales[0] = tabla_enero;

        Map<Float,Float> tabla_febrero = new TreeMap<Float,Float>();

        tabla_febrero.put(1666.67f,0.09f);
        tabla_febrero.put(3333.33f,0.14f);
        tabla_febrero.put(5000f,0.19f);
        tabla_febrero.put(10000f,0.23f);
        tabla_febrero.put(15000f,0.27f);
        tabla_febrero.put(20000f,0.31f);

        tabla_retenciones_mensuales[1] = tabla_febrero;

        Map<Float,Float> tabla_marzo = new TreeMap<Float,Float>();

        tabla_marzo.put(2500f,0.09f);
        tabla_marzo.put(5000f,0.14f);
        tabla_marzo.put(7500f,0.19f);
        tabla_marzo.put(15000f,0.23f);
        tabla_marzo.put(22500f,0.27f);
        tabla_marzo.put(30000f,0.31f);

        tabla_retenciones_mensuales[2] = tabla_marzo;

        Map<Float,Float> tabla_abril = new TreeMap<Float,Float>();

        tabla_abril.put(3333.33f,0.09f);
        tabla_abril.put(6666.67f,0.14f);
        tabla_abril.put(10000f,0.19f);
        tabla_abril.put(20000f,0.23f);
        tabla_abril.put(30000f,0.27f);
        tabla_abril.put(40000f,0.31f);

        tabla_retenciones_mensuales[3] = tabla_abril;

        Map<Float,Float> tabla_mayo = new TreeMap<Float,Float>();

        tabla_mayo.put(4166.67f,0.09f);
        tabla_mayo.put(8333.33f,0.14f);
        tabla_mayo.put(12500f,0.19f);
        tabla_mayo.put(25000f,0.23f);
        tabla_mayo.put(37500f,0.27f);
        tabla_mayo.put(50000f,0.31f);

        tabla_retenciones_mensuales[4] = tabla_mayo;

        Map<Float,Float> tabla_junio = new TreeMap<Float,Float>();

        tabla_junio.put(5000f,0.09f);
        tabla_junio.put(10000f,0.14f);
        tabla_junio.put(15000f,0.19f);
        tabla_junio.put(30000f,0.23f);
        tabla_junio.put(45000f,0.27f);
        tabla_junio.put(60000f,0.31f);

        tabla_retenciones_mensuales[5] = tabla_junio;


        Map<Float,Float> tabla_julio = new TreeMap<Float,Float>();

        tabla_julio.put(5833.33f,0.09f);
        tabla_julio.put(11666.67f,0.14f);
        tabla_julio.put(17500f,0.19f);
        tabla_julio.put(35000f,0.23f);
        tabla_julio.put(52500f,0.27f);
        tabla_julio.put(70000f,0.31f);

        tabla_retenciones_mensuales[6] = tabla_julio;

        Map<Float,Float> tabla_agosto = new TreeMap<Float,Float>();

        tabla_agosto.put(6666.67f,0.09f);
        tabla_agosto.put(13333.33f,0.14f);
        tabla_agosto.put(20000f,0.19f);
        tabla_agosto.put(40000f,0.23f);
        tabla_agosto.put(60000f,0.27f);
        tabla_agosto.put(80000f,0.31f);

        tabla_retenciones_mensuales[7] = tabla_agosto;

        Map<Float,Float> tabla_septiembre = new TreeMap<Float,Float>();

        tabla_septiembre.put(7500f,0.09f);
        tabla_septiembre.put(15000f,0.14f);
        tabla_septiembre.put(22500f,0.19f);
        tabla_septiembre.put(45000f,0.23f);
        tabla_septiembre.put(67500f,0.27f);
        tabla_septiembre.put(90000f,0.31f);

        tabla_retenciones_mensuales[8] = tabla_septiembre;

        Map<Float,Float> tabla_octubre = new TreeMap<Float,Float>();

        tabla_octubre.put(8333.33f,0.09f);
        tabla_octubre.put(16666.67f,0.14f);
        tabla_octubre.put(25000f,0.19f);
        tabla_octubre.put(50000f,0.23f);
        tabla_octubre.put(75000f,0.27f);
        tabla_octubre.put(100000f,0.31f);

        tabla_retenciones_mensuales[9] = tabla_octubre;

        Map<Float,Float> tabla_noviembre = new TreeMap<Float,Float>();

        tabla_noviembre.put(9166.67f,0.09f);
        tabla_noviembre.put(18333.33f,0.14f);
        tabla_noviembre.put(27500f,0.19f);
        tabla_noviembre.put(55000f,0.23f);
        tabla_noviembre.put(82500f,0.27f);
        tabla_noviembre.put(110000f,0.31f);

        tabla_retenciones_mensuales[10] = tabla_noviembre;

        Map<Float,Float> tabla_diciembre = new TreeMap<Float,Float>();

        tabla_diciembre.put(10000f,0.09f);
        tabla_diciembre.put(20000f,0.14f);
        tabla_diciembre.put(30000f,0.19f);
        tabla_diciembre.put(60000f,0.23f);
        tabla_diciembre.put(90000f,0.27f);
        tabla_diciembre.put(120000f,0.31f);

        tabla_retenciones_mensuales[11] = tabla_noviembre;



        Set<Float> keys = tabla_retenciones_mensuales[0].keySet();

        for ( Float key : keys ) {
            System.out.println(key);
        }
    }
}
