package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class fragmentPaginaPrincipal extends Fragment implements View.OnClickListener {
    ImageView iconoRut;
    Button comida,sup,rut;
    TextView txtNombre,txtPrinRut,txtSecRut,txtmsj;
    Usuario usr;
    MainActivity main;
    Integer dia;
    Boolean fRut;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_paginaprincipal, container, false);
        main =(MainActivity) getActivity();
        comida=vista.findViewById(R.id.btnPrinCom);
        sup=vista.findViewById(R.id.imageViewMusculoPrincipal);
        rut=vista.findViewById(R.id.btnPrinFit);
        txtNombre = vista.findViewById(R.id.txtNombreEnPagPrincipal);
        iconoRut=vista.findViewById(R.id.iconoEj);
        txtPrinRut=vista.findViewById(R.id.txtPrinRut);
        txtSecRut=vista.findViewById(R.id.txtSecRut);
        txtmsj=vista.findViewById(R.id.txtmsj);
        usr = main.devolverUsuarioActivo();

        if( usr.get_Nombre()==null)
        {
            txtNombre.setText("Hola Fede");

        }
        else{
            txtNombre.setText("Hola " + usr.get_Nombre());
        }

        Double h = usr.get_Dedicacion();
        int ded = h.intValue();

        dia = main.devolverDiaDeLaSemana();

        switch (ded){

            case 1:
                break;
            case 2:
                if(dia == 3 || dia == 5){
                    //Se activa el boton rutina
                    rut.setEnabled(true);
                }
                else {
                    //Se desactiva el boton rutina
                    rut.setEnabled(false);

                }
                break;
            case 3:
                if(dia == 2 || dia == 4 || dia == 6){
                    //Se activa el boton rutina
                    rut.setEnabled(true);

                }
                else {
                    //Se desactiva el boton rutina
                    rut.setEnabled(false);

                }
                break;
            case 4:
                if(dia == 2 || dia == 4|| dia == 5|| dia == 6){
                    //Se activa el boton rutina
                    rut.setEnabled(true);

                }
                else {
                    //Se desactiva el boton rutina
                    rut.setEnabled(false);

                }
                break;

            case 5:
                if(dia == 2|| dia == 3 || dia == 4|| dia == 5|| dia == 6){
                    //Se activa el boton rutina
                    rut.setEnabled(true);

                }
                else {
                    //Se desactiva el boton rutina
                    rut.setEnabled(false);

                }
                break;




        }
        fRut=main.finalizarRutina();
        if(fRut==true)
        {
            txtmsj.setText("Completaste tu rutina, sigue así!");
            txtSecRut.setText("Completado");
            txtPrinRut.setText("Haz terminado tu rutina!");
            rut.setBackgroundResource(R.drawable.slected);
            rut.setEnabled(false);
            iconoRut.setImageDrawable(getResources().getDrawable(R.drawable.icon_ionic_md_ready));

            //Date dia = new Date();
            //java.util.Calendar calendar1 = java.util.Calendar.getInstance();
            //calendar1.add(java.util.Calendar.DAY_OF_MONTH,0);
            //events.add(new EventDay(calendar1, R.drawable.circ_rutina));
            //main.recebirCal(events);
        }


        comida.setOnClickListener(this);
        sup.setOnClickListener(this);
        rut.setOnClickListener(this);
        return vista;
    }
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(botonApretado.getId() == comida.getId()){
            main.pasarAcomida();
        }
        if(botonApretado.getId() == sup.getId()){

            main.pasarATrenSuperior();
        }
        if(botonApretado.getId() == rut.getId()){
            main.pasarARutina();
        }

    }
}
