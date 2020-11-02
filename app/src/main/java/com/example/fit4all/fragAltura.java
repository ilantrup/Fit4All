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
public class fragAltura extends Fragment implements View.OnClickListener {
    Button flechaD;
    EditText edtxAlt;
    Usuario usr;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_altura,container,false);
        flechaD=vista.findViewById(R.id.btnFlechaAltura);
        edtxAlt= vista.findViewById(R.id.edxAlturaRegister);
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
            try {
               String p = edtxAlt.getText().toString(); // Same
                Double d = Double.parseDouble(p);// Make use of autoboxing.  It's also easier to read.
                usr.set_Altura(d);
                if (usr.get_Altura() != 0.0) {
                    main.setUsuarioACrear(usr);
                    main.pasarApeso();
                } else {
                    main.alertaNoIngreso();
                }
            } catch (NumberFormatException e) {
                main.alertaNoIngreso();
            }


        }
    }
}
