package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;
import com.mikhaellopez.lazydatepicker.LazyDatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fragEdad extends Fragment implements View.OnClickListener {
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    Date today = new Date();
    private static final String DATE_FORMAT = "dd-MM-yyyy" ;
    Date minDate = LazyDatePicker.stringToDate("01-01-1930", DATE_FORMAT);
    Date maxDate = LazyDatePicker.stringToDate(df.format(today), DATE_FORMAT);
    Button flechaD;
    LazyDatePicker DP;
    Usuario usr;
    MainActivity main;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_edad,container,false);
        flechaD=vista.findViewById(R.id.btnFlechaEdad);
        DP=vista.findViewById(R.id.lazyDatePicker);
        // init
        // dpDate.init(2002, 10, 27, null);
        flechaD.setOnClickListener(this);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();
        return vista;
    }
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(flechaD.getId()== botonApretado.getId()){
            Date d = DP.getDate();
            StringBuilder builder=new StringBuilder();
            builder.append("Current Date: ");
            builder.append((d.getMonth() + 1)+"/");//month is 0 based
            builder.append(d.getDay()+"/");
            builder.append(d.getYear());
            Log.d("Conexion", builder.toString());





                usr.set_Edad(d);
                if (usr.get_Edad().toString() != "") {
                    main.setUsuarioACrear(usr);
                    main.pasarAdedicacion();
                } else {
                    main.alertaNoIngreso();
                }
        }
    }
}
