package com.argentina.salarycalculator.calculadoradesueldo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.argentina.salarycalculator.calculadoradesueldo.R;
import com.argentina.salarycalculator.calculadoradesueldo.utils.CalculadoraSueldo;
import com.argentina.salarycalculator.calculadoradesueldo.utils.JubilacionStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.ObraSocialStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.PamiStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.SindicatoStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;


public class PlaceHolderFragment extends Fragment {

    private InterstitialAd mInterstitialAd;

    private Map<Float,Float>[] tabla_retenciones_mensuales;

    private Activity mainActivity;

    private Button button;
    private RadioButton rb_si;
    private RadioButton rb_no;
    private EditText et_salario;
    private EditText et_sindicato;
    private EditText et_hijos;
    private EditText et_resultado;
    private Map<Integer,String> respuestasValidacion_sueldoBruto_minimo = new HashMap<Integer,String>();
    private Map<Integer,String> respuestasValidacion_sueldoBruto_maximo = new HashMap<Integer,String>();
    private Map<Integer,String> respuestasValidacion_sindicato = new HashMap<Integer,String>();
    private static final Float salarioMinimo = 4716f;
    private static final Float salarioMaximo = 1000000f;

    private Integer lastSueldoBrutoMinimoValidation = 0;
    private Integer lastSueldoBrutoMaximoValidation = 0;
    private Integer lastSindicatoValidation = 0;

    private Integer MAX_SUELDO_BRUTO_MINIMO_VALIDATION = 0;
    private Integer MAX_SUELDO_BRUTO_MAXIMO_VALIDATION = 0;
    private Integer MAX_SINDICATO_VALIDATION = 0;

    public PlaceHolderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        et_salario = (EditText) rootView.findViewById(R.id.editText_salario);
        et_sindicato = (EditText) rootView.findViewById(R.id.editText_sindicato);
        et_hijos = (EditText) rootView.findViewById(R.id.editText_hijos);

        rb_si = (RadioButton) rootView.findViewById(R.id.radioButton_si);
        rb_no = (RadioButton) rootView.findViewById(R.id.radioButton_no);

        creacionFrasesValidacion();
        initButton(rootView);
        initAd();


        return rootView;
    }

    private void creacionFrasesValidacion(){
        respuestasValidacion_sueldoBruto_minimo.put(1, "poca guita, superá el salario minimo, master");
        respuestasValidacion_sueldoBruto_minimo.put(2, "poca guita, no manejamos salarios bajo cero");
        respuestasValidacion_sueldoBruto_minimo.put(3, "poca guita, anotá mi numero y te presto plata");
        respuestasValidacion_sueldoBruto_minimo.put(0, "poca guita, nunca vas a viajar a europa");
        respuestasValidacion_sueldoBruto_minimo.put(4, "poca guita, estas para comer arroz y agua");
        respuestasValidacion_sueldoBruto_minimo.put(5, "poca guita, olvidate del asado con amigos");
        respuestasValidacion_sueldoBruto_minimo.put(6, "poca guita, estas para dormir abajo del puente");
        respuestasValidacion_sueldoBruto_minimo.put(7, "poca guita, no te cobran ganancias ni muerto");
        respuestasValidacion_sueldoBruto_minimo.put(8, "poca guita, estas contando las moneditas?");

        respuestasValidacion_sueldoBruto_maximo.put(0, "mucha guita, anda a navegar al delta");
        respuestasValidacion_sueldoBruto_maximo.put(1, "mucha guita, andate ibiza negro");
        respuestasValidacion_sueldoBruto_maximo.put(2, "mucha guita, agarrate una modelo titán");
        respuestasValidacion_sueldoBruto_maximo.put(3, "mucha guita, podes ser del jet set");
        respuestasValidacion_sueldoBruto_maximo.put(4, "mucha guita, guarda con los punguistas en el subte");
        respuestasValidacion_sueldoBruto_maximo.put(5, "mucha guita, nunca lavaste un plato");


        respuestasValidacion_sindicato.put(1, "error, superaste el maximo, cambia de sindicato");
        respuestasValidacion_sindicato.put(0, "error, tu sindicato esta lavando guita");
        respuestasValidacion_sindicato.put(2, "error, tu sindicato te estafa");
        respuestasValidacion_sindicato.put(3, "error, sos un esclavo de tu sindicato");


        MAX_SUELDO_BRUTO_MINIMO_VALIDATION = respuestasValidacion_sueldoBruto_minimo.size();
        MAX_SUELDO_BRUTO_MAXIMO_VALIDATION = respuestasValidacion_sueldoBruto_maximo.size();
        MAX_SINDICATO_VALIDATION = respuestasValidacion_sindicato.size();

    }

    private void initButton(View rootView) {

        rb_no.setChecked(true);

        // Create the "retry" button, which tries to show an interstitial between game plays.
        button = (Button) rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation()){
                    displayAd();
                    calcularSueldoNeto();

                }
            }
        });

        rb_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearRadioButtons();
                rb_no.setChecked(true);
            }
        });
        rb_si.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearRadioButtons();
                rb_si.setChecked(true);
            }
        });
    }
    @Override
    public void onResume() {
        // Initialize the timer if it hasn't been initialized yet.
        // Start the game.
        super.onResume();
        System.out.println("onResume");

        initAd();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState");

    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mainActivity = activity;
    }

    @Override
    public void onPause() {
        // Cancel the timer if the game is paused.

        super.onPause();
        System.out.println("onPause");
    }

    private void initAd() {
        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(mainActivity);
        // Defined in values/strings.xml
        mInterstitialAd.setAdUnitId(getString(R.string.test_pantalla_completa_ad_unit_id));
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                //Toast.makeText(mainActivity,"The interstitial is loaded", Toast.LENGTH_SHORT).show();
            }
/*
                @Override
                public void onAdClosed() {
                    // Proceed to the next level.

                    System.out.println("onAdClosed");
                    calcularSueldoNeto();
                }*/
        });

        mInterstitialAd.loadAd(adRequest);



    }

    private void displayAd() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();
            calcularSueldoNeto();
        }
    }


    private boolean validation(){
        String salario_string = et_salario.getText().toString();
        String sindicato_string = et_sindicato.getText().toString();
        String hijos_string = et_hijos.getText().toString();

        boolean isValid = true;

        if(salario_string==null || salario_string.equals("")){

            int r = new Random().nextInt(MAX_SUELDO_BRUTO_MINIMO_VALIDATION);
            while(lastSueldoBrutoMinimoValidation == r){
                r = new Random().nextInt(MAX_SUELDO_BRUTO_MINIMO_VALIDATION);
            }
            lastSueldoBrutoMinimoValidation = r;
            et_salario.setError(respuestasValidacion_sueldoBruto_minimo.get(r));
            isValid=false;

        }else if(Float.valueOf(salario_string)<salarioMinimo){

            int r = new Random().nextInt(MAX_SUELDO_BRUTO_MINIMO_VALIDATION);
            while(lastSueldoBrutoMinimoValidation == r){
                r = new Random().nextInt(MAX_SUELDO_BRUTO_MINIMO_VALIDATION);
            }
            lastSueldoBrutoMinimoValidation = r;
            et_salario.setError(respuestasValidacion_sueldoBruto_minimo.get(r));
            isValid=false;
        }else if(Float.valueOf(salario_string)>salarioMaximo){

            int r = new Random().nextInt(MAX_SUELDO_BRUTO_MAXIMO_VALIDATION);

            while(lastSueldoBrutoMaximoValidation == r){
                r = new Random().nextInt(MAX_SUELDO_BRUTO_MAXIMO_VALIDATION);
            }
            lastSueldoBrutoMaximoValidation = r;
            et_salario.setError(respuestasValidacion_sueldoBruto_maximo.get(r));
            isValid=false;
        }
        if(hijos_string!=null && !hijos_string.equals("")&&Float.valueOf(hijos_string)>8){
            et_hijos.setError("hasta 8 hijos");
            isValid=false;
        }

        if(sindicato_string!=null && !sindicato_string.equals("")){
            if(Float.valueOf(sindicato_string)<0 || Float.valueOf(sindicato_string)>10){
                int r = new Random().nextInt(MAX_SINDICATO_VALIDATION);
                while(lastSindicatoValidation == r){
                    r = new Random().nextInt(MAX_SINDICATO_VALIDATION);
                }
                lastSindicatoValidation = r;
                et_sindicato.setError(respuestasValidacion_sindicato.get(r));
                isValid=false;

            }
        }

        if(isValid){
            return true;
        }else{
            return false;
        }

    }

    private void calcularSueldoNetoPorMes(){


        float salario=0f;
        float sindicato=0f;
        float descuento_jubilacion = 0f;
        float descuento_obrasocial = 0f;
        float subtotal = 0f;
        final float APORTES_JUBILATORIOS = 0.11f;
        final float OBRA_SOCIAL = 0.03f;
        Integer hijos = 0;

        String salario_string = et_salario.getText().toString();
        String sindicato_string = et_sindicato.getText().toString();
        String hijos_string = et_hijos.getText().toString();

        float descuento_jubilacion_mensual = 0f;
        float descuento_obrasocial_mensual = 0f;

        float medio_aguinaldo=salario/2;

        float salario_acumulado = 0f;
        float salario_temp=0;

        for(int mes=1;mes<13;mes++){

            if(mes==6 || mes==12){
                salario_temp = salario + medio_aguinaldo;
            }else{
                salario_temp=salario;
            }

            salario_acumulado+=salario_temp;

            //tabla_retenciones_mensuales[mes]
        }

    }

    private void calcularSueldoNeto() {

        float salario=0f;
        float descuento_jubilacion = 0f;
        float descuento_obrasocial = 0f;
        float subtotal = 0f;
        final float APORTES_JUBILATORIOS = 0.11f;
        final float OBRA_SOCIAL = 0.03f;
        Integer hijos = 0;

        String salario_string = et_salario.getText().toString();
        String sindicato_string = et_sindicato.getText().toString();
        String hijos_string = et_hijos.getText().toString();


        salario = Float.valueOf(salario_string);

        float jubilacion = JubilacionStrategy.calcularJubilacion(salario);
        float obrasocial = ObraSocialStrategy.calcularObraSocial(salario);
        float pami = PamiStrategy.calcularPami(salario);
        float sindicato = SindicatoStrategy.calcularSindicato(salario, sindicato_string);

        float salario_con_aguinaldo = salario + salario/2;

        float jubilacion_con_aguinaldo = JubilacionStrategy.calcularJubilacion(salario_con_aguinaldo);
        float obrasocial_con_aguinaldo = ObraSocialStrategy.calcularObraSocial(salario_con_aguinaldo);
        float pami_con_aguinaldo = PamiStrategy.calcularPami(salario_con_aguinaldo);
        float sindicato_con_aguinaldo = SindicatoStrategy.calcularSindicato(salario_con_aguinaldo, sindicato_string);

        Float subtotalGananciaImponible = salario
                - jubilacion
                - obrasocial
                - pami
                - sindicato;

        Float subtotalGananciaImponible_con_aguinaldo = salario_con_aguinaldo
                - jubilacion_con_aguinaldo
                - obrasocial_con_aguinaldo
                - pami_con_aguinaldo
                - sindicato_con_aguinaldo;


        Map<String,Integer> variables = new HashMap<String,Integer>();
        boolean casado =false;
        if(rb_si.isChecked()){
            casado=true;
            variables.put("casado",1);
        }else if (rb_no.isChecked()){
            casado=false;
            variables.put("casado",0);

        }
        if(hijos_string!=null && !hijos_string.equals("")){
            hijos = Integer.valueOf(hijos_string);
            variables.put("hijos",hijos);

        }else{
            variables.put("hijos",0);

        }
        variables.put("parientes",0);

        Map<String,Float[]> resultado = CalculadoraSueldo.calcular(subtotalGananciaImponible,new ArrayList(),sindicato_string,variables);

        Float[] a_pagar_mes_a_mes = resultado.get("a_pagar_mes");

        Float ganancias = 0f;
        for (Float gasto : a_pagar_mes_a_mes) {
            ganancias +=gasto;
        }

        //Float ganancias_mensual_SIN_AGUINALDO = ganancias/12;
        Float ganancias_mensual_SIN_AGUINALDO = a_pagar_mes_a_mes[1];

        Float total = subtotalGananciaImponible - ganancias_mensual_SIN_AGUINALDO;

        Float total_con_aguinaldo = subtotalGananciaImponible_con_aguinaldo - a_pagar_mes_a_mes[7];

        Map<String,Float> variable_con_aguinaldo = new HashMap<String,Float>();
        Map<String,Float> variable = new HashMap<String,Float>();

        variable.put("sueldoBruto",salario);
        variable.put("jubilacion",jubilacion);
        variable.put("obrasocial",obrasocial);
        variable.put("pami",pami);
        variable.put("sindicato",sindicato);
        variable.put("sueldoNeto",total);
        variable.put("ganancias_mensual",ganancias_mensual_SIN_AGUINALDO);


        variable_con_aguinaldo.put("sueldoBruto_con_aguinaldo",salario_con_aguinaldo);
        variable_con_aguinaldo.put("jubilacion_con_aguinaldo",jubilacion_con_aguinaldo);
        variable_con_aguinaldo.put("obrasocial_con_aguinaldo",obrasocial_con_aguinaldo);
        variable_con_aguinaldo.put("pami_con_aguinaldo",pami_con_aguinaldo);
        variable_con_aguinaldo.put("sindicato_con_aguinaldo",sindicato_con_aguinaldo);
        variable_con_aguinaldo.put("sueldoNeto_con_aguinaldo",total_con_aguinaldo);
        variable_con_aguinaldo.put("ganancias_mensual_con_aguinaldo",a_pagar_mes_a_mes[7]);

        //SueldoNetoDetailFragment sueldoNetoDetailFragment = SueldoNetoDetailFragment.newInstance(salario, jubilacion, obrasocial, sindicato_1, ganancias_mensual_SIN_AGUINALDO, total, pami);
        SueldoNetoDetailFragment sueldoNetoDetailFragment = SueldoNetoDetailFragment.newInstance(variable,variable_con_aguinaldo);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,sueldoNetoDetailFragment)
                .addToBackStack(null)
                .commit();

    }




    private void clearRadioButtons(){
        rb_no.setChecked(false);
        rb_si.setChecked(false);
    }


    private float calcular(){


        Set keys = tabla_retenciones_mensuales[0].keySet();

        return 0f;
    }

    private void crearTable(){
        tabla_retenciones_mensuales = new TreeMap[12];

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
    }
}