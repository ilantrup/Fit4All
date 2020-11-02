package com.example.fit4all;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class fragPerfil extends Fragment implements View.OnClickListener  {
    TextView  sexo, edad, peso, altura, ded, obj, nom,cita;
    Button log,edt, cerrasSesion;
    Usuario usr;
    MainActivity main;
    ImageView imvFoto;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_perfil,container,false);
        main =(MainActivity) getActivity();
        usr = main.devolverUsuarioActivo();
        edt=vista.findViewById(R.id.txtEditarEnPerfil);
        log=vista.findViewById(R.id.txtLogroEnPerfil);
        cerrasSesion=vista.findViewById(R.id.btnLogOut);
        log.setOnClickListener(this);
        edt.setOnClickListener(this);
        cerrasSesion.setOnClickListener(this);
        cita=vista.findViewById(R.id.txtCitaEnPerfil);
        nom=vista.findViewById(R.id.nombrePerfil);
        sexo=vista.findViewById(R.id.txtSexoEnPerfil);
        edad=vista.findViewById(R.id.txtEdadEnPerfil);
        peso=vista.findViewById(R.id.txtPesoEnPerfil);
        altura=vista.findViewById(R.id.txtAlturaEnPerfil);
        ded=vista.findViewById(R.id.txtDedicacionEnPerfil);
        obj=vista.findViewById(R.id.txtObjetivoEnPerfil);
        imvFoto= vista.findViewById(R.id.imageViewFotoPerfil);




        Calendar cal = main.toCalendar(usr.get_Edad());

        Integer dia = cal.get(Calendar.DAY_OF_MONTH);
        Integer mes = cal.get(Calendar.MONTH);
        Integer anio = cal.get(Calendar.YEAR);
        Integer ed = main.getAge(anio, mes, dia);
        Integer pe = Integer.valueOf(usr.get_Peso().intValue());
        Integer al = Integer.valueOf(usr.get_Altura().intValue());
        Integer de = Integer.valueOf(usr.get_Dedicacion().intValue());


        Context con = imvFoto.getContext();
        nom.setText(usr.get_Nombre() +" " + usr.get_Apellido());
        sexo.setText("Sexo: " + usr.get_Sexo());
        edad.setText("Edad: " + ed.toString() + " años");
        peso.setText("Peso: " + pe.toString() + " KG");
        altura.setText("Altura: " + al.toString()+ " CM");
        ded.setText("Dedicacion: " + de.toString() + " veces por semana");
        obj.setText("Objetivo: " + usr.get_Objetivo());
        cita.setText(usr.get_Cita());
        //Picasso.with(con).load(usr.get_Foto()).into(imvFoto);





        return vista;
    }


    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;
        if(edt.getId()== botonApretado.getId()){
            MainActivity main=(MainActivity) getActivity();
            main.pasarAEditar();
        }
        if(log.getId()== botonApretado.getId()){
            MainActivity main=(MainActivity) getActivity();
            main.pasarALogros();
        }
        if(cerrasSesion.getId()== botonApretado.getId()){
            MainActivity main=(MainActivity) getActivity();
            main.cerrarSesion();
        }

    }
}
