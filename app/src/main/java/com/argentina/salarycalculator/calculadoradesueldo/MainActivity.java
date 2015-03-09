package com.argentina.salarycalculator.calculadoradesueldo;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import com.argentina.salarycalculator.calculadoradesueldo.utils.JubilacionStrategy;
import com.argentina.salarycalculator.calculadoradesueldo.utils.ObraSocialStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

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
            return rootView;
        }

        private void initButton(View rootView) {
            // Create the "retry" button, which tries to show an interstitial between game plays.
            button = (Button) rootView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

            if(salario_string!=null && Float.valueOf(salario_string)>1){
                salario = Float.valueOf(salario_string);
            }else{
                System.out.println("salario "+salario);

            }

            if(sindicato_string!=null && !sindicato_string.equals("")){
                sindicato = Float.valueOf(sindicato_string);
            }

            if(hijos_string!=null && !hijos_string.equals("")){
                hijos = Integer.valueOf(hijos_string);
            }

            Float total = salario
                    - JubilacionStrategy.calcularJubilacion(salario)
                    - ObraSocialStrategy.calcularObraSocial(salario);


            et_resultado.setText(total.toString());

        }




        private void clearRadioButtons(){
            rb_no.setChecked(false);
            rb_si.setChecked(false);
        }

    }
    /*
    public static class AdFragment extends Fragment {

        private AdView mAdView;

        public AdFragment() {
        }

        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);

            mAdView = (AdView) getView().findViewById(R.id.adView);

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            mAdView.loadAd(adRequest);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }

        @Override
        public void onPause() {
            if (mAdView != null) {
                mAdView.pause();
            }
            super.onPause();
        }

        @Override
        public void onResume() {
            super.onResume();
            if (mAdView != null) {
                mAdView.resume();
            }
        }

        @Override
        public void onDestroy() {
            if (mAdView != null) {
                mAdView.destroy();
            }
            super.onDestroy();
        }

    }
    */
}
