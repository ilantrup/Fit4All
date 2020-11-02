package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class fragIngresoCita extends Fragment implements View.OnClickListener {
    Button flechaD;
    EditText edtxCita;
    Usuario usr;
    MainActivity main;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_ingresocita,container,false);
        flechaD=vista.findViewById(R.id.btnFlechaDerCita);
        flechaD.setOnClickListener(this);

        edtxCita= vista.findViewById(R.id.edxCitaMotivadora);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();


        return vista;
    }
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(flechaD.getId()== botonApretado.getId()){
            usr.set_Cita(edtxCita.getText().toString());
            if (usr.get_Cita() != "") {
                main.setUsuarioACrear(usr);
                main.pasarAregistroDatos();
            } else {
                main.alertaNoIngreso();
            }
        }
    }
}



