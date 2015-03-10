package com.argentina.salarycalculator.calculadoradesueldo;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SueldoNetoDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SueldoNetoDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SueldoNetoDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SUELDO_BRUTO = "sueldoBruto";
    private static final String JUBILACION = "jubilacion";
    private static final String OBRA_SOCIAL = "obraSocial";
    private static final String SINDICATO = "sindicato";
    private static final String GANANCIAS = "ganancias";
    private static final String PAMI = "pami";
    private static final String SUELDO_NETO = "sueldoNeto";

    // TODO: Rename and change types of parameters

    private Float sueldo_bruto;
    private Float jubilacion;
    private Float obraSocial;
    private Float sindicato;
    private Float ganancias;
    private Float sueldoNeto;
    private Float pami;

    //private OnFragmentInteractionListener mListener;


    public static SueldoNetoDetail newInstance(Float sueldoBruto, Float jubilacion,Float obraSocial,Float sindicato,Float ganancias,Float sueldoNeto,Float pami) {
        SueldoNetoDetail fragment = new SueldoNetoDetail();
        Bundle args = new Bundle();

        args.putFloat(SUELDO_BRUTO,sueldoBruto);
        args.putFloat(JUBILACION,jubilacion);
        args.putFloat(OBRA_SOCIAL,obraSocial);
        args.putFloat(SINDICATO,sindicato);
        args.putFloat(GANANCIAS,ganancias);
        args.putFloat(SUELDO_NETO,sueldoNeto);
        args.putFloat(PAMI,pami);

        fragment.setArguments(args);
        return fragment;
    }

    public SueldoNetoDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sueldo_bruto = getArguments().getFloat(SUELDO_BRUTO);
            jubilacion = getArguments().getFloat(JUBILACION);
            sindicato = getArguments().getFloat(SINDICATO);
            obraSocial = getArguments().getFloat(OBRA_SOCIAL);
            sueldoNeto = getArguments().getFloat(SUELDO_NETO);
            ganancias = getArguments().getFloat(GANANCIAS);
            pami = getArguments().getFloat(PAMI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sueldo_neto_detail, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        FrameLayout root = (FrameLayout) view;
        Context context = view.getContext();
        assert context != null;

        View item = LayoutInflater.from(context).inflate(R.layout.fragment_sueldo_neto_detail, root, false);
        assert item!=null;
        
        bind(item);
    }



    // TODO: Rename method, update argument and hook method into UI event
    /*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
       */
    private void bind(View parent) {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        TextView textView_sueldo_bruto = (TextView) parent.findViewById(R.id.textView_sueldo_bruto);
        TextView textView_sueldo_neto = (TextView) parent.findViewById(R.id.textView_sueldo_neto);
        TextView textView_jubilacion = (TextView) parent.findViewById(R.id.textView_jubilacion);
        TextView textView_obra_social = (TextView) parent.findViewById(R.id.textView_obra_social);
        TextView textView_pami = (TextView) parent.findViewById(R.id.textView_pami);
        TextView textView_ganancias = (TextView) parent.findViewById(R.id.textView_ganancias);
        TextView textView_sindicato = (TextView) parent.findViewById(R.id.textView_sindicato);

        textView_sueldo_bruto.setText(sueldo_bruto.toString());

        textView_sueldo_neto.setText(sueldoNeto.toString());
        textView_jubilacion.setText(jubilacion.toString());
        textView_obra_social.setText(obraSocial.toString());
        textView_pami.setText(pami.toString());
        textView_ganancias.setText(ganancias.toString());
        textView_sindicato.setText(sindicato.toString());

    }

    /*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    */
    /*
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}
