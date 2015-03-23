package com.argentina.salarycalculator.calculadoradesueldo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.argentina.salarycalculator.calculadoradesueldo.activity.MainActivity;

/**
 * Created by Matias on 15/03/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity > {
    private MainActivity mFirstTestActivity;
    private TextView salario_bruto_textfield;
    private TextView salario_neto_textview;
    private Button calcular_button;
    private FragmentManager fragmentManager;

    public MainActivityTest() {
        super("com.argentina.salarycalculator.calculadoradesueldo.activity",MainActivity.class);
    }

    public void testApp(){

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        mFirstTestActivity = getActivity();

        fragmentManager = mFirstTestActivity.getFragmentManager();

        salario_bruto_textfield =(TextView) mFirstTestActivity.findViewById(R.id.editText_salario);
        calcular_button = (Button) mFirstTestActivity.findViewById(R.id.button);
        salario_neto_textview = (TextView) mFirstTestActivity.findViewById(R.id.textView_sueldo_neto);
    }

    public void testSalarioBruto_sin_condiciones(){

        mFirstTestActivity.runOnUiThread(new Runnable() {
            public void run(){
                salario_bruto_textfield.setText("20000");
                calcular_button.performClick();
            }
        });

        Fragment fragment = mFirstTestActivity.getFragmentManager().findFragmentById(R.layout.fragment_sueldo_neto_detail);
        //fragment.getResources().getText(R.id.textView_sueldo_neto);
        //assertEquals(salario_neto_textview.getText(), "14546.42");
        //calcular_button.set

        //mFirstTestActivity.finish();


    }
}
