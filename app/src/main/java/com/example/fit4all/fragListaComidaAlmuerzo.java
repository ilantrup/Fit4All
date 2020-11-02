package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragListaComidaAlmuerzo extends Fragment{

    private RecyclerView recyclerViewComida1,recyclerViewComida2;
    private RecyclerView.Adapter adapter1,adapter2;
    private RecyclerView.LayoutManager Manager1,Manager2;
    ArrayList<plato> platosArrayList1= new ArrayList<>();
    ArrayList<plato> platosArrayList2= new ArrayList<>();
    plato unPlato= new plato();
    plato unPlato2= new plato();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_lista_comida,container,false);
        unPlato._nombre="Ensalda de Pollo";
        unPlato._nutrientes="Proteinas";
        unPlato._desc="aaaaaaaaaaaaaa";
        unPlato._imagen=getResources().getDrawable(R.drawable.ensalada_de_pollo);
        unPlato2._nombre="Ensalda de Pollos";
        unPlato2._nutrientes="Proteinassss";
        unPlato2._desc="aaaaaaaaaaaaaa";
        unPlato2._imagen=getResources().getDrawable(R.drawable.ensalada_de_pollo);
        platosArrayList1.add(unPlato);
        platosArrayList1.add(unPlato);
        platosArrayList1.add(unPlato);
        platosArrayList2.add(unPlato2);
        platosArrayList2.add(unPlato2);
        recyclerViewComida1 = vista.findViewById(R.id.recycleComida1);
        recyclerViewComida2 = vista.findViewById(R.id.recycleComida2);
        Manager1 = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) Manager1).setOrientation(LinearLayoutManager.HORIZONTAL);
        Manager2 = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) Manager2).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewComida1.setLayoutManager(Manager1);
        recyclerViewComida2.setLayoutManager(Manager2);
        Log.d("Conexion","Error" );
        adapter1 =  new adaptadorDePlatosRecycle(getActivity(),platosArrayList1);
        adapter2 =  new adaptadorDePlatosRecycle2(getActivity(),platosArrayList2);
        recyclerViewComida1.setAdapter(adapter1);
        recyclerViewComida2.setAdapter(adapter2);
        return vista;
    }
}
