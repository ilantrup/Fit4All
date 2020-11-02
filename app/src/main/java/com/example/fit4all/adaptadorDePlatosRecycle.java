package com.example.fit4all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class adaptadorDePlatosRecycle extends  RecyclerView.Adapter<adaptadorDePlatosRecycle.ViewHolder> {
    private ArrayList<plato> arrayPlato;
    private LayoutInflater mInflater;



    adaptadorDePlatosRecycle(Context context, ArrayList<plato> data) {
        this.mInflater = LayoutInflater.from(context);
        this.arrayPlato = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_una_comida, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        plato miPlato= arrayPlato.get(position);
        ViewHolder Holder=(ViewHolder) holder;
        Holder.nomb.setText(miPlato._nombre);
        Holder.desc.setText(miPlato._desc);
        Holder.nutri.setText(miPlato._nutrientes);
        Holder.Imagen.setImageDrawable(miPlato._imagen);

    }



    @Override
    public int getItemCount() {
        return arrayPlato.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomb, nutri, desc;
        ImageView Imagen;


        ViewHolder(View itemView) {
            super(itemView);
            nomb = itemView.findViewById(R.id.NombrePlato);
            nutri = itemView.findViewById(R.id.NutrientesPlato);
            desc = itemView.findViewById(R.id.DescPlato);
            Imagen = itemView.findViewById(R.id.imageViewFotoPlato);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main=(MainActivity) v.getContext();
                    plato unPlato=new plato();
                    unPlato._nombre=arrayPlato.get(getAdapterPosition())._nombre;
                    unPlato._desc= arrayPlato.get(getAdapterPosition())._desc;
                    unPlato._nutrientes= arrayPlato.get(getAdapterPosition())._nutrientes;
                    unPlato._imagen= arrayPlato.get(getAdapterPosition())._imagen;
                    main.recebirDatosUnplato(unPlato);
                    main.pasarAPlato();
                }
            });
        }


    }
    }

