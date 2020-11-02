package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
public class fragmentOnboardingBloq extends Fragment implements View.OnClickListener {
    Button flechaD,flechaI;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_onboardingbloqueado, container, false);
        flechaD = vista.findViewById(R.id.imageViewFlechaDerObloq);
        flechaI = vista.findViewById(R.id.imageViewFlechaIzqObloq);
        flechaD.setOnClickListener(this);
        flechaI.setOnClickListener(this);

        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado = (Button) vista;
        if (botonApretado.getId() == flechaD.getId()) {
            MainActivity main = (MainActivity) getActivity();
            main.pasarAonboraingCom();
        }
    }
}
