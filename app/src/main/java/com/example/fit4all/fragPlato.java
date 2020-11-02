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

public class fragPlato extends Fragment {
    ImageView img;
    TextView nomb,nut,desc,guard;
    Button btn;
    plato unPlato=new plato();
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_plato, container, false);
        img=vista.findViewById(R.id.FotoUnPlato);
        nomb=vista.findViewById(R.id.NombreUnPlato);
        nut=vista.findViewById(R.id.NutrientesUnPlato);
        desc=vista.findViewById(R.id.DescUnPlato);
        btn=vista.findViewById(R.id.SwitchGuardarOff);
        guard=vista.findViewById(R.id.GuardadoUnPlato);
        MainActivity main=(MainActivity) getActivity();
        unPlato=main.traerDatos();
        nomb.setText(unPlato._nombre);
        desc.setText(unPlato._desc);
        nut.setText(unPlato._nutrientes);
        guard.setText("Guardar");
        img.setImageDrawable(unPlato._imagen);
        return vista;
    }
}
