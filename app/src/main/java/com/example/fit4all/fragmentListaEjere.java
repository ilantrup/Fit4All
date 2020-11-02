package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class fragmentListaEjere extends Fragment implements View.OnClickListener{
    ListView lista;
    ArrayList<Ejercicio> ejArrayList= new ArrayList<>();
    adaptadorDeEjercicios ejAdapter;
    ArrayList<Ejercicio> Ej = new ArrayList<Ejercicio>();
    ArrayList<Ejercicio> id= new ArrayList<Ejercicio>();
    zonaDeEjercicio zona= new zonaDeEjercicio();
    Button btn;
    MainActivity main;
    ArrayList<String> Listade3Ejs = new ArrayList<String>();
    ArrayList<String> Listade3EjsMedio = new ArrayList<String>();
    TextView txtEj;

    ArrayList<Ejercicio> arrayDeEjerciciosDeCalentamiento = new ArrayList<>();
    ArrayList<Ejercicio> arrayDeEstiramientosDeCalentamiento = new ArrayList<>();

    ArrayList<Ejercicio> arrayCompleto = new ArrayList<>();
    ArrayList<Ejercicio> arrayFinal = new ArrayList<>();





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_lista_ejerciocios, container, false);
        lista=vista.findViewById(R.id.listaEjer);
        txtEj=vista.findViewById(R.id.txtCantEj);
        btn=vista.findViewById(R.id.btnComRut);
        btn.setOnClickListener(this);
        main= (MainActivity) getActivity();
        zona=main.devolverZona();
        Log.d("Fede", String.valueOf(zona.get_idZonaDeEjercicio()));
        ArrayList<String> arraycal = new ArrayList<>();



        if(zona.get_idZonaDeEjercicio()=="Superior")
        {
            id=main.devolverListaInferior();
            arraycal = main.traerCalentamientoInf();

        }
        else if (zona.get_idZonaDeEjercicio()=="Inferior")
        {
            id=main.devolverListaInferior();
            arraycal = main.traerCalentamientoSup();

        }
        else
        {
            id=main.devolverListaMedio();
            arraycal = main.traerCalentamientoMed();

        }



        for (int i =0; i < arraycal.size(); i++){

            if (i < 3){
                Ejercicio ejDeCal = main.traerEjSegunId(arraycal.get(i));
                arrayDeEjerciciosDeCalentamiento.add(ejDeCal);

            }
            if (i >=3 ){
                Ejercicio esDeCal = main.traerEstiramientoSegunId(arraycal.get(i));
                arrayDeEstiramientosDeCalentamiento.add(esDeCal);

            }

        }


        if (zona.get_idZonaDeEjercicio() != "Media") {
            Listade3Ejs = main.ListaDe3Ejs();
            if (Listade3Ejs == null || Listade3Ejs.size() < 1) {
                Ej = main.randomEjerId(id);
            } else {
                //Aca va el traer ejercicio segun id
                for (int i = 0; i < Listade3Ejs.size(); i++) {

                    Ejercicio j = main.traerEjSegunId(Listade3Ejs.get(i));
                    String s = j.get_NombreEjercicio();
                    Ej.add(j);
                    Log.d("traerEjSegunId", s);

                }
            }
        }
        else {
            Listade3EjsMedio = main.ListaDe3EjsMedio();
            if (Listade3EjsMedio == null || Listade3EjsMedio.size() < 1) {
                Ej = main.randomEjerIdMedio(id);
            } else {
                //Aca va el traer ejercicio segun id
                for (int i = 0; i < Listade3EjsMedio.size(); i++) {

                    Ejercicio j = main.traerEjSegunId(Listade3EjsMedio.get(i));
                    String s = j.get_NombreEjercicio();
                    Ej.add(j);
                    Log.d("traerEjSegunId", s);

                }
            }

        }


        arrayCompleto.addAll(arrayDeEjerciciosDeCalentamiento);
        arrayCompleto.addAll(arrayDeEstiramientosDeCalentamiento);
        arrayCompleto.addAll(Ej);

        for(int i = 0; i < arrayCompleto.size(); ++i)
        {

                Ejercicio ejer= new Ejercicio();
                if(arrayCompleto.get(i).get_NombreEjercicio()!="") {
                    ejer.set_NombreEjercicio(arrayCompleto.get(i).get_NombreEjercicio());
                    ejer.set_img(getResources().getDrawable(R.drawable.flexiones_img));
                    ejer.set_Foto(arrayCompleto.get(i).get_Foto());
                    ejer.set_seg(arrayCompleto.get(i).get_Seg());
                }
                else {
                    ejer.set_NombreEjercicio("Elongación de " + arrayCompleto.get(i).get_Musculos().get(0));
                    ejer.set_img(getResources().getDrawable(R.drawable.flexiones_img));
                    ejer.set_Foto(arrayCompleto.get(i).get_Foto());
                    ejer.set_seg(5.0);
                }

                arrayFinal.add(ejer);
        }
        ejAdapter = new adaptadorDeEjercicios(arrayFinal,getActivity());
        lista.setAdapter(ejAdapter);
        txtEj.setText("Ejercicios:" + arrayFinal.size());
        return vista;
    }


    @Override
    public void onClick(View v) {
        Button botonApretado;
        botonApretado= (Button) v;

        if(btn.getId()== botonApretado.getId()){
            main.recibiArrayEj(arrayFinal);
            main.pasarASerieEjer();
        } }

}