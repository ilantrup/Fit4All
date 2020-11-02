package com.example.fit4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorDeZonas extends BaseAdapter {
    private ArrayList<zonaDeEjercicio> arrayZona;
    zonaDeEjercicio zona= new zonaDeEjercicio();
    private Context miContexto;
    TextView Nomb,intro;
    ImageView imgPrin,Icon;
    Button Btn;
    public adaptadorDeZonas (ArrayList<zonaDeEjercicio> arrLog, Context contexto) {
        arrayZona = arrLog;
        miContexto = contexto;
    }

    @Override
    public int getCount() {
        return arrayZona.size();
    }

    @Override
    public zonaDeEjercicio getItem(int position) {
        zonaDeEjercicio ej;
        ej = arrayZona.get(position);
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
        vista = inflador.inflate(R.layout.layout_tipo_ejercicio, parent, false);
        Nomb = vista.findViewById(R.id.NombreTipoDeEjercicio);
        Nomb.setFocusable(false);
        intro = vista.findViewById(R.id.textoIntoTipoEjer);
        intro.setFocusable(false);
        Icon= vista.findViewById(R.id.iconoTipoDeEjercicio);
        Icon.setFocusable(false);
        imgPrin = vista.findViewById(R.id.imagenTipoDeEjercicio);
        imgPrin.setFocusable(false);
        Btn= vista.findViewById(R.id.btnTipoDeEjercicio);
        Btn.setFocusable(false);
        zona=getItem(position);
        Nomb.setText(zona.get_idZonaDeEjercicio());
        Icon.setImageDrawable(miContexto.getResources().getDrawable(R.drawable.icon_ionic_md_fitness));
        imgPrin.setImageDrawable(zona.get_Foto());
        Btn.setText("Listo!");
        intro.setText("Siguiente");
        Btn.setBackgroundResource(R.drawable.login);
       if(zona.get_finish()==true)
        {
         intro.setText("Completado");
         Btn.setBackgroundResource(R.drawable.slected);
         Btn.setText("Listo!");
         Icon.setImageDrawable(miContexto.getResources().getDrawable(R.drawable.icon_ionic_md_ready));
        }
        return vista;
    }



}
