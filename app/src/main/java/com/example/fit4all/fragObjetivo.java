package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
public class fragObjetivo extends Fragment implements View.OnClickListener {
        Button BFU,BPU,BFS,BPS,BFN,BPN, Flecha;
        Usuario usr;
        MainActivity main;
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_objetivo,container,false);
        BFU=vista.findViewById(R.id.btnFuerzaUnsellected);
        BFS=vista.findViewById(R.id.btnFuerzaSellected);
        BFN=vista.findViewById(R.id.btnFuerzaNo);
        BPU=vista.findViewById(R.id.btnPerderPesoUnsellected);
        BPS=vista.findViewById(R.id.btnPerderPesoSellected);
        BPN=vista.findViewById(R.id.btnPerderNo);
        Flecha=vista.findViewById(R.id.btnFlechaObjetivo);
        BFU.setOnClickListener(this);
        BPU.setOnClickListener(this);
        BPS.setOnClickListener(this);
        BFN.setOnClickListener(this);
        BPN.setOnClickListener(this);
        BFS.setOnClickListener(this);
        Flecha.setOnClickListener(this);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();
        return vista;
        }
public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;
        if(botonApretado.getId()==BFU.getId())
        {
        BFU.setVisibility(View.GONE);
        BFS.setVisibility(View.VISIBLE);
        BPU.setVisibility(View.GONE);
        BPN.setVisibility(View.VISIBLE);
        usr.set_Objetivo("Ganar fuerza");
        }
        if(botonApretado.getId()==BPU.getId())
        {
        BFU.setVisibility(View.GONE);
        BFN.setVisibility(View.VISIBLE);
        BPU.setVisibility(View.GONE);
        BPS.setVisibility(View.VISIBLE);
        usr.set_Objetivo("Perder peso");
        }
        if(botonApretado.getId()==BPN.getId())
        {
        BFS.setVisibility(View.GONE);
        BFN.setVisibility(View.VISIBLE);
        BPN.setVisibility(View.GONE);
        BPS.setVisibility(View.VISIBLE);
        usr.set_Objetivo("Perder peso");
        }
        if(botonApretado.getId()==BFN.getId())
        {
        BFN.setVisibility(View.GONE);
        BFS.setVisibility(View.VISIBLE);
        BPS.setVisibility(View.GONE);
        BPN.setVisibility(View.VISIBLE);
        usr.set_Objetivo("Ganar fuerza");
        }
        if(botonApretado.getId()==Flecha.getId())
        { if (usr.get_Objetivo() != "") {
                main.setUsuarioACrear(usr);
                main.pasarAfoto();
        } else {
                main.alertaNoIngreso();
        }
        }
        }
        }
