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
import com.argentina.salarycalculator.calculadoradesueldo.utils.GananciasStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.JubilacionStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.ObraSocialStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.PamiStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.SindicatoStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class PlaceholderFragment extends Fragment {

    private InterstitialAd mInterstitialAd;

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
                displayAd();
                calcularSueldoNeto();
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
                Toast.makeText(mainActivity,
                        "The interstitial is loaded", Toast.LENGTH_SHORT).show();
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

        if(salario_string!=null && !salario_string.equals("")){
            if( Float.valueOf(salario_string)>1){
                salario = Float.valueOf(salario_string);
            }else{
                et_salario.setError("el valor tiene que ser mayor que cero");
            }

        }else{
            et_salario.setError("el valor tiene que ser mayor que cero");
            System.out.println("salario "+salario);

        }

        if(sindicato_string!=null && !sindicato_string.equals("")){

            sindicato = Float.valueOf(sindicato_string);

            if(sindicato<0){
                System.out.println("salario "+salario);

            }
        }

        if(hijos_string!=null && !hijos_string.equals("")){
            hijos = Integer.valueOf(hijos_string);
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


        boolean casado =false;
        if(rb_si.isChecked()){
            casado=true;
        }else if (rb_no.isChecked()){
            casado=false;
        }
        int parientes = 0;
        float ganancias = GananciasStrategy.calcularGanancia(subtotalGananciaImponible, casado, hijos, parientes);

        Float total = subtotalGananciaImponible - ganancias;


        SueldoNetoDetailFragment sueldoNetoDetailFragment = SueldoNetoDetailFragment.newInstance(salario, jubilacion, obrasocial, sindicato_1, ganancias, total, pami);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,sueldoNetoDetailFragment)
                        // We push the fragment transaction to back stack. User can go back to the
                        // previous fragment by pressing back button.
                .addToBackStack(null)
                .commit();
        //et_resultado.setText(total.toString());

    }




    private void clearRadioButtons(){
        rb_no.setChecked(false);
        rb_si.setChecked(false);
    }

}
