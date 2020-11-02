package com.example.fit4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adaptadorDeEjercicios extends BaseAdapter {
    private ArrayList<Ejercicio> arrrayEj;
    Ejercicio ejer= new Ejercicio();
    private Context miContexto;
    TextView Nomb,rep;
    ImageView imgPrin;
    public adaptadorDeEjercicios (ArrayList<Ejercicio> arrLog, Context contexto) {
        arrrayEj = arrLog;
        miContexto = contexto;
    }

    @Override
    public int getCount() {
        return arrrayEj.size();
    }

    @Override
    public Ejercicio getItem(int position) {
        Ejercicio ej;
        ej = arrrayEj.get(position);
        return ej;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vista;
        LayoutInflater inflador;
        inflador = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vista = inflador.inflate(R.layout.layout_un_ejercicio, parent, false);
        Nomb = vista.findViewById(R.id.nomEj);
        rep = vista.findViewById(R.id.txtRep);
        imgPrin = vista.findViewById(R.id.imgEj);
        ejer=getItem(position);
        Nomb.setText(ejer.get_NombreEjercicio());
       // imgPrin.setImageDrawable(ejer.get_imagen());
        if (!ejer.get_Foto().equals("")){
            Picasso.with(imgPrin.getContext()).load(ejer.get_Foto()).resize(500,300).into(imgPrin);
        }
        rep.setText( ejer.get_Seg()+"Seg");
        return vista;
    }


}