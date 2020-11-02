package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragListaTipoEjerciciosSuperior  extends Fragment {
    private RecyclerView recyclerViewTipoEjercicio;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager Manager;
    ArrayList<TipoEjercicio> tipoEjerciciosArrayList= new ArrayList<>();
    TipoEjercicio typeEx= new TipoEjercicio();
    TipoEjercicio typeEx2= new TipoEjercicio();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_lista_tipo_ejercicio_superior, container, false);
        recyclerViewTipoEjercicio = vista.findViewById(R.id.RecycleTipoEjerciciosSuperior);
        typeEx._nombre="Brazos";
        typeEx._imagen=getResources().getDrawable(R.drawable.rutinaprin);
        typeEx._rango=2;
        typeEx2._nombre="plancha";
        typeEx2._imagen=getResources().getDrawable(R.drawable.rutinaprin);
        typeEx2._rango=2;
        tipoEjerciciosArrayList.add(typeEx2);
        tipoEjerciciosArrayList.add(typeEx);
        Manager= new LinearLayoutManager(getActivity());
        recyclerViewTipoEjercicio.setLayoutManager(Manager);
        adapter =  new adaptadorDeTiposEjerciciosRecycle(tipoEjerciciosArrayList,getActivity());
        recyclerViewTipoEjercicio.setAdapter(adapter);
        return vista;
    }
}
