package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class fragDedicacion extends Fragment implements View.OnClickListener {
    Button flechaD;
    EditText edtxDed;
    Usuario usr;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_dedicacion,container,false);
        flechaD=vista.findViewById(R.id.btnFlechaDedicacion);
        flechaD.setOnClickListener(this);
        edtxDed= vista.findViewById(R.id.edxDedicacionRegister);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();
        return vista;
    }
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(flechaD.getId()== botonApretado.getId()){
            try {
                String p = edtxDed.getText().toString(); // Same
                Double d = Double.parseDouble(p);// Make use of autoboxing.  It's also easier to read.
                if (d >=1.0 && d <= 5.0) {
                    usr.set_Dedicacion(d);
                    if (usr.get_Dedicacion() != 0.0) {
                        main.setUsuarioACrear(usr);
                        main.pasarAobjetivo();
                    } else {
                        main.alertaNoIngreso();
                    }
                } else{main.alertaNoIngreso();}
            } catch (NumberFormatException e) {
                main.alertaNoIngreso();
            }
        }
    }
}