package com.example.fit4all;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adaptadorDeLogros extends BaseAdapter {
private ArrayList<logro> arrayLogros;
        logro miLogro= new logro();
private Context miContexto;
        ImageView img;
        TextView fecha,Nomb;
public adaptadorDeLogros (ArrayList<logro> arrLog, Context contexto) {
        arrayLogros = arrLog;
        miContexto = contexto;
        }

@Override
public int getCount() {
        return arrayLogros.size();
        }

@Override
public logro getItem(int position) {
        logro unLogro;
        unLogro = arrayLogros.get(position);
        return unLogro;
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
        vista = inflador.inflate(R.layout.layout_logro, parent, false);
        Nomb = vista.findViewById(R.id.nombreLogro);
        fecha = vista.findViewById(R.id.fechaLogro);
        img = vista.findViewById(R.id.imagenLogro);

        miLogro=getItem(position);
        if(miLogro.getunBlocked()==true) {
        Nomb.setText(miLogro.get_nombres());
        fecha.setText(String.valueOf(miLogro.get_fecha().DATE)+"/"+String.valueOf(miLogro.get_fecha().MONTH)+"/"+String.valueOf(miLogro.get_fecha().YEAR));
        Picasso.with(img.getContext()).load(miLogro.get_url()).into(img);
        }

        return vista;
        }


        }
