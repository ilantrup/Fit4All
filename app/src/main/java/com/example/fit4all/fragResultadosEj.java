package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class fragResultadosEj  extends Fragment implements View.OnClickListener {
    Button btnSalir;
    zonaDeEjercicio zona= new zonaDeEjercicio();
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_resultados_train,container,false);
        main= (MainActivity) getActivity();
        zona=main.devolverZona();
        zona.set_finish(true);
        if (zona.get_idZonaDeEjercicio().equals("Superior")){
            main.setearSupTrue();
        }
        else if (zona.get_idZonaDeEjercicio().equals("Inferior"))
        {
            main.setearInfTrue();
        }
        else {
            main.setearMedTrue();
        }

        btnSalir= vista.findViewById(R.id.volverPrin);
        btnSalir.setOnClickListener(this);
        return  vista;
    }

    @Override
    public void onClick(View v) {
        Button botonApretado;
        botonApretado= (Button) v;
        if(botonApretado.getId()==btnSalir.getId()){
            main.iListaEj = 0;
            main.pasarANav();

        }
    }
}
