package com.example.fit4all;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adaptadorDeTiposEjerciciosRecycle extends RecyclerView.Adapter{
    private ArrayList<TipoEjercicio> arrayTypeEx;
    private Context miContexto;

    public adaptadorDeTiposEjerciciosRecycle (ArrayList<TipoEjercicio> arrEx, Context contexto) {
        this.arrayTypeEx = arrEx;
        this.miContexto = contexto;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View vista = LayoutInflater.from(miContexto).inflate(R.layout.layout_tipo_ejercicio,null);
        return new adaptadorDeTiposEjerciciosRecycle.Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TipoEjercicio miTypeEx= arrayTypeEx.get(position);
        adaptadorDeTiposEjerciciosRecycle.Holder Holder=(adaptadorDeTiposEjerciciosRecycle.Holder) holder;
        Holder.Nomb.setText(miTypeEx._nombre);
        Holder. Icon.setImageDrawable(miContexto.getResources().getDrawable(R.drawable.icon_ionic_md_fitness));
        Holder.imgPrin.setImageDrawable(miTypeEx._imagen);
        Holder.Btn.setText("Listo!");
        if(miTypeEx._finish==false)
        {
            Holder.intro.setText("Siguiente");
            if(miTypeEx._rango!=10){
                Holder.Btn.setBackgroundResource(R.drawable.login);
            }
            else{
                Holder.Btn.setBackgroundResource(R.drawable.register);
                Holder.Icon.setImageDrawable(miContexto.getResources().getDrawable(R.drawable.icon_ionic_md_fitness));
            }
        }
        else {
            Holder.intro.setText("Finalizado");
            Holder.Btn.setBackgroundResource(R.drawable.slected);
        }
    }

    @Override
    public int getItemCount() {
        return arrayTypeEx.size();
    }




    public static class Holder extends RecyclerView.ViewHolder{
        TextView Nomb,intro;
        ImageView imgPrin,Icon;
        Button Btn;


        public Holder(@NonNull View vista) {
            super(vista);
            Nomb = vista.findViewById(R.id.NombreTipoDeEjercicio);
            intro = vista.findViewById(R.id.textoIntoTipoEjer);
            imgPrin = vista.findViewById(R.id.imagenTipoDeEjercicio);
            Icon= vista.findViewById(R.id.iconoTipoDeEjercicio);
            Btn= vista.findViewById(R.id.btnTipoDeEjercicio);
        }
    }


}