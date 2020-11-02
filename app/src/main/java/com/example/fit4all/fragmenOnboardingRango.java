package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
public class fragmenOnboardingRango extends Fragment implements View.OnClickListener {
    Button flecha;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_onboardingrango, container, false);
        flecha = vista.findViewById(R.id.imageViewFlechaOrang);
        flecha.setOnClickListener(this);

        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado = (Button) vista;
        if (botonApretado.getId() == flecha.getId()) {
            MainActivity main = (MainActivity) getActivity();
            main.pasarAonboraingBloq();
        }
    }
}
