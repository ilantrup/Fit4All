package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class fragmentSexo extends Fragment implements View.OnClickListener {
    Button BMU,BHU,BMS,BHS,BMN,BHN, Flecha;
    Usuario usr;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista=inflater.inflate(R.layout.layout_sexo,container,false);
        BMU=vista.findViewById(R.id.btnMujerUnsellect);
        BMS=vista.findViewById(R.id.btnMujerSelected);
        BMN=vista.findViewById(R.id.btnMujerNo);
        BHU=vista.findViewById(R.id.btnHombreUnsellect);
        BHS=vista.findViewById(R.id.btnHombreSellect);
        BHN=vista.findViewById(R.id.btnHombreNo);
        Flecha=vista.findViewById(R.id.btnFlechaSexo);
        BMU.setOnClickListener(this);
        BHU.setOnClickListener(this);
        BHS.setOnClickListener(this);
        BMN.setOnClickListener(this);
        BHN.setOnClickListener(this);
        BMS.setOnClickListener(this);
        Flecha.setOnClickListener(this);
        usr = new Usuario();
        main = (MainActivity) getActivity();
        usr = main.devolverUsuarioACrear();


        return vista;
    }
    public void onClick(View vista) {
    Button botonApretado;
    botonApretado= (Button) vista;
    if(botonApretado.getId()==BMU.getId())
    {
        BMU.setVisibility(View.GONE);
        BMS.setVisibility(View.VISIBLE);
        BHU.setVisibility(View.GONE);
        BHN.setVisibility(View.VISIBLE);
        usr.set_Sexo("Femenino");
    }
    if(botonApretado.getId()==BHU.getId())
        {
            BMU.setVisibility(View.GONE);
            BMN.setVisibility(View.VISIBLE);
            BHU.setVisibility(View.GONE);
            BHS.setVisibility(View.VISIBLE);
            usr.set_Sexo("Masculino");
        }
        if(botonApretado.getId()==BHN.getId())
        {
            BMS.setVisibility(View.GONE);
            BMN.setVisibility(View.VISIBLE);
            BHN.setVisibility(View.GONE);
            BHS.setVisibility(View.VISIBLE);
            usr.set_Sexo("Masculino");
        }
        if(botonApretado.getId()==BMN.getId())
        {
            BMN.setVisibility(View.GONE);
            BMS.setVisibility(View.VISIBLE);
            BHS.setVisibility(View.GONE);
            BHN.setVisibility(View.VISIBLE);
            usr.set_Sexo("Femenino");
        }
        if(botonApretado.getId()==Flecha.getId())
        {
            if (usr.get_Sexo() != "") {
                main.setUsuarioACrear(usr);
                main.pasarAaltura();
            } else {
            main.alertaNoIngreso();
            }
        }
    }
}
