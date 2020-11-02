package com.example.fit4all;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;import android.widget.AdapterView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class fragPerfilLogros extends Fragment {
    ListView lista;
    ArrayList<logro> logroArrayList= new ArrayList<>();
    ArrayList<tipoEvento> eventosArrayList= new ArrayList<>();
    adaptadorDeLogros logrosAdapter;
    logro unLogro= new logro();
    int a=0,i=0;
    MainActivity main;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_perfillogoros, container, false);
        lista=vista.findViewById(R.id.lista);
        main =(MainActivity) getActivity();
        logroArrayList=main.devolverArrayLogro();
        eventosArrayList=main.devolverArrayCal();
        if(logroArrayList.get(0).getunBlocked()==false)
        {
            while (a != 1 &&  i > eventosArrayList.size() )
            {

                if(i<eventosArrayList.size()) {
                    if (eventosArrayList.get(i).getTipo() == true) {
                        a++;
                    }
                }
                i++;

            }
            if (a==1)
            {
                java.util.Date fecha = new Date();
                main.cambiarLogros(fecha,"1");
                logroArrayList.get(0).setunBlocked(true);
            }
        }
        a=0; i=0;
        if(logroArrayList.get(1).getunBlocked()==false)
        {
            while (a != 3 &&  i < eventosArrayList.size() )
            {
                if(i<eventosArrayList.size()) {
                    if (eventosArrayList.get(i).getTipo() == true) {
                        a++;
                    }
                }
                i++;

            }
            if (a==3)
            {
                logroArrayList.get(1).setunBlocked(true);

            }
        }
        a=0; i=0;
        if(logroArrayList.get(2).getunBlocked()==false)
        {
            while (a != 7 &&  i < eventosArrayList.size() )
            {
                if(i<eventosArrayList.size()) {
                    if (eventosArrayList.get(i).getTipo() == true) {
                        a++;
                    }
                }
                i++;
            }
            if (a==7)
            {
                logroArrayList.get(2).setunBlocked(true);
            }
        }
        //unLogro.setImg(getResources().getDrawable(R.drawable.logro_max_brazo));
        logrosAdapter = new  adaptadorDeLogros(logroArrayList,getActivity());
        lista.setAdapter(logrosAdapter);
        return vista;
    }




}