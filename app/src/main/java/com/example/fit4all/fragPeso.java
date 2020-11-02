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
public class fragPeso extends Fragment implements View.OnClickListener {
    Button flechaD;
    EditText edtxPeso;
    Usuario usr;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_peso,container,false);
        flechaD=vista.findViewById(R.id.imageButtonFlechaDerPeso);
        flechaD.setOnClickListener(this);
        edtxPeso= vista.findViewById(R.id.edxPesoRegister);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();
        return vista;
    }
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(botonApretado.getId() == flechaD.getId()){
            try {
                String p = edtxPeso.getText().toString(); // Same
                Double d = Double.parseDouble(p);// Make use of autoboxing.  It's also easier to read.
                usr.set_Peso(d);
                if (usr.get_Peso() != 0.0) {
                    main.setUsuarioACrear(usr);
                    main.pasarAedad();
                } else {
                    main.alertaNoIngreso();
                }
            } catch (NumberFormatException e) {
                main.alertaNoIngreso();
            }
        }
    }
}
