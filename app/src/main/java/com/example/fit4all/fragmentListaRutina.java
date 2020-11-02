package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class fragmentListaRutina extends Fragment {
    ListView lista;
    ArrayList<zonaDeEjercicio> zonaArrayList= new ArrayList<>();
    adaptadorDeZonas zonasAdapter;
    MainActivity main;
    zonaDeEjercicio zonaOnclick= new zonaDeEjercicio();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_rutina_menu, container, false);
        lista=vista.findViewById(R.id.listaRutina);
        main= (MainActivity) getActivity();
        String Zona;
        if (main.traerZona().equals("")) {
            Zona = main.randomSupInf();
            main.guardarZona(Zona);
        }
        else {
            Zona= main.traerZona();
        }
            if (Zona == "Superior") {
                zonaDeEjercicio unaZona = main.devolverSup();
                zonaArrayList.add(unaZona);
            } else {
                zonaDeEjercicio unaZona = main.devolverInf();
                zonaArrayList.add(unaZona);
            }
            zonaDeEjercicio unaZona = main.devolverMed();
            zonaArrayList.add(unaZona);


        zonasAdapter = new adaptadorDeZonas(zonaArrayList,getActivity());
        lista.setClickable(true);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main=(MainActivity) getActivity();
                main.recebirZona(zonaArrayList.get(position));
                zonaOnclick= main.devolverZona();
                if(zonaOnclick.get_finish()!=true)
                {
                    main.pasarAejercicio();
                }
            }
        });
        lista.setAdapter(zonasAdapter);


        return vista;
    }
}
