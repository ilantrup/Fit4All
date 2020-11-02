package com.example.fit4all;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class fragPerfilEditar extends Fragment implements View.OnClickListener{
    Button right,left,conf;
    EditText edxP,edxA,edxMail;
    TextView txtDed,txtNom,txtCita,edxfech,edxsex;
    Usuario usr=new Usuario();
    MainActivity main;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_perfileditar,container,false);
        main =(MainActivity) getActivity();
        usr = main.devolverUsuarioActivo();
        right=vista.findViewById(R.id.imgbArrowRightDedicacion);
        left=vista.findViewById(R.id.imgbArrowLeftDedicacion);
        edxP=vista.findViewById(R.id.edxPeso);
        edxA=vista.findViewById(R.id.edxAltura);
        txtDed=vista.findViewById(R.id.txtDedicacionEnEditText);
        txtNom=vista.findViewById(R.id.nombreEditPerfil);
        txtCita=vista.findViewById(R.id.txtCitaEnEditPerfil);
        edxsex=vista.findViewById(R.id.edxSexo);
        edxfech=vista.findViewById(R.id.edxFecha);
        edxMail=vista.findViewById(R.id.edxMailnEditText);
        conf=vista.findViewById(R.id.btnConfCambio);
        right.setOnClickListener(this);
        left.setOnClickListener(this);
        conf.setOnClickListener(this);
        txtNom.setText(usr.get_Nombre() +" " + usr.get_Apellido());
        txtCita.setText(usr.get_Cita());
        edxsex.setText(usr.get_Sexo());
        edxfech.setText(usr.get_Edad().toString());
        edxP.setText(usr.get_Peso().toString());
        edxA.setText(usr.get_Altura().toString());
        txtDed.setText(usr.get_Dedicacion().toString() + " veces por semana");

        return vista;
    }


    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;
        if(right.getId()== botonApretado.getId()){
            if(txtDed.getText().toString().equals("3.0 veces por semana"))
            {
                txtDed.setText("4.0 veces por semana");
            }
            else if(txtDed.getText().toString().equals("4.0 veces por semana"))
            {
                txtDed.setText("5.0 veces por semana");
            }
           else if(txtDed.getText().toString().equals("2.0 veces por semana"))
            {
                txtDed.setText("3.0 veces por semana");
            }
           else if(txtDed.getText().toString().equals("1.0 veces por semana"))
            {
                txtDed.setText("2.0 veces por semana");
            }
        }
        if(left.getId()== botonApretado.getId()){
            if(txtDed.getText().toString().equals("3.0 veces por semana"))
            {
                txtDed.setText("2.0 veces por semana");
            }
           else if(txtDed.getText().toString().equals("4.0 veces por semana"))
            {
                txtDed.setText("3.0 veces por semana");

            }
            else if(txtDed.getText().toString().equals("2.0 veces por semana"))
            {
                txtDed.setText("1.0 veces por semana");
            }
            else if(txtDed.getText().toString().equals("5.0 veces por semana"))
            {
                txtDed.setText("4.0 veces por semana");
            }
        }

        if(conf.getId()==botonApretado.getId())
        {
            String Sded=txtDed.getText().toString();
            String SdedSub=Sded.substring(0, 3);
            Double Alt= Double.parseDouble(edxA.getText().toString());
            Double Pes= Double.parseDouble(edxP.getText().toString());
            Double Ded= Double.parseDouble(SdedSub);

            //Double dedi= Double.parseDouble(txtDed.getText().toString());
            //Date edad= parseDate(edxfech.getText().toString());
            main =(MainActivity) getActivity();
            usr = main.devolverUsuarioActivo();
            usr.set_Sexo(edxsex.getText().toString());
            usr.set_Altura(Alt);
            usr.set_Peso(Pes);
            usr.set_Dedicacion(Ded);
            //usr.set_Edad(edad);
            //usr.set_Dedicacion(dedi);

            main.setUsuarioActivo(usr);
            main.cambiarDatos(usr);
        }
    }
    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


}
