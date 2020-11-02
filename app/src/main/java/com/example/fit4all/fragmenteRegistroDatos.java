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
public class fragmenteRegistroDatos extends Fragment implements View.OnClickListener{
    Button flechaD;
    EditText edtxNom, edtxApe;
    Usuario usr;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_registrodatos,container,false);
        flechaD=vista.findViewById(R.id.btnFlechaDerRegistrodatos);

        flechaD.setOnClickListener(this);

        edtxNom= vista.findViewById(R.id.edxName);
        edtxApe= vista.findViewById(R.id.edxSurname);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        String N = main.devolverNombreNoSeguro();
        String A = main.devolverApellidoNoSeguro();
        edtxNom.setText(N);
        edtxApe.setText(A);
        usr = main.devolverUsuarioACrear();
        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;

        if(flechaD.getId()== botonApretado.getId()){
            usr.set_Nombre(edtxNom.getText().toString());
            usr.set_Apellido(edtxApe.getText().toString());
            if (usr.get_Nombre() != "" && usr.get_Apellido() != "") {
                main.setUsuarioACrear(usr);
                main.pasarAcompletado();
            } else {
                main.alertaNoIngreso();
            }
        }

    }
}
