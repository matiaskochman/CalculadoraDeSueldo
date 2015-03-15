package com.argentina.salarycalculator.calculadoradesueldo.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.argentina.salarycalculator.calculadoradesueldo.utils.GananciasStrategy;
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
import java.util.Set;
import java.util.TreeMap;


public class PlaceholderFragment extends Fragment {

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

    public PlaceholderFragment() {
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
        et_resultado = (EditText) rootView.findViewById(R.id.editText_resultado);

        initButton(rootView);
        initAd();


        return rootView;
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
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
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

        if(salario_string==null || salario_string.equals("")){
            et_salario.setError("el valor tiene que ser mayor que cero");
            return false;

        }
        return true;
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

            if( Float.valueOf(salario_string)>1){
                salario = Float.valueOf(salario_string);
            }else{
                et_salario.setError("el valor tiene que ser mayor que cero");
            }
        /*
        if(salario_string!=null && !salario_string.equals("")){

        }else{
            et_salario.setError("el valor tiene que ser mayor que cero");
            System.out.println("salario "+salario);

        }*/

        if(sindicato_string!=null && !sindicato_string.equals("")){

            sindicato = Float.valueOf(sindicato_string);

            if(sindicato<0){
                System.out.println("sindicato "+sindicato);

            }
        }


        float jubilacion = JubilacionStrategy.calcularJubilacion(salario);
        float obrasocial = ObraSocialStrategy.calcularObraSocial(salario);
        float pami = PamiStrategy.calcularPami(salario);
        float sindicato_1 = SindicatoStrategy.calcularSindicato(salario, sindicato_string);



        Float subtotalGananciaImponible = salario
                - jubilacion
                - obrasocial
                - pami
                - sindicato_1;

        /*

        int parientes = 0;
        float ganancias = GananciasStrategy.calcularGanancia(subtotalGananciaImponible, casado, hijos, parientes);

        */

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

        Float ganancias_mensual = ganancias/12;
        Float total = subtotalGananciaImponible - ganancias_mensual;


        SueldoNetoDetailFragment sueldoNetoDetailFragment = SueldoNetoDetailFragment.newInstance(salario, jubilacion, obrasocial, sindicato_1, ganancias_mensual, total, pami);
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
