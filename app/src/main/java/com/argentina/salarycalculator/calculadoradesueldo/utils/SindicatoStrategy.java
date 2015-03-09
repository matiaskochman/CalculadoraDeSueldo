package com.argentina.salarycalculator.calculadoradesueldo.utils;

/**
 * Created by Matias on 09/03/2015.
 */
public class SindicatoStrategy {

    public static float calcularSindicato(float salarioBruto, String porcentajeSindicato){

        float sindicatoValor = 0f;
        if(porcentajeSindicato!=null && !porcentajeSindicato.equals("")){
            sindicatoValor = Float.valueOf(porcentajeSindicato);
        }

        float sindicatoPorcentaje = sindicatoValor/100f;
        Float totalSindicato = salarioBruto*sindicatoPorcentaje;
        return totalSindicato;
    }

/*
    public static float calcularSindicato(float salarioBruto, ActionBarActivity activity,View view,String porcentajeSindicato){

        if(porcentajeSindicato!=null && porcentajeSindicato.equals("666")){
            SindicatoFragment sindicatoFragment = SindicatoFragment.newInstance(R.drawable.moyano,"il diabolo",
                    (int) view.getX(), (int) view.getY(),view.getWidth(), view.getHeight());
            //ArticleFragment newFragment = new ArticleFragment();
            Bundle args = new Bundle();
            //args.putInt(ArticleFragment.ARG_POSITION, position);
            //newFragment.setArguments(args);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,sindicatoFragment)
                    .addToBackStack(null)
                    .commit();

        }
        float sindicatoValor = 0f;
        if(porcentajeSindicato!=null && !porcentajeSindicato.equals("")){
            sindicatoValor = Float.valueOf(porcentajeSindicato);
        }

        float sindicatoPorcentaje = sindicatoValor/100f;
        Float totalSindicato = salarioBruto*sindicatoPorcentaje;
        return totalSindicato;
    }
*/
}
