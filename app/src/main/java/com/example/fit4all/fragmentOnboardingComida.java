package com.example.fit4all;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
public class fragmentOnboardingComida extends Fragment implements View.OnClickListener{
    ImageView flechaI;
    Button listo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_onboadingcomida, container, false);
        listo= vista.findViewById(R.id.btnListoOn);
        flechaI = vista.findViewById(R.id.imageViewFlechaIzqComida);
        listo.setOnClickListener(this);
        flechaI.setOnClickListener(this);

        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado = (Button) vista;
        if (botonApretado.getId() == listo.getId()) {
            MainActivity main = (MainActivity) getActivity();
            main.pasarAingresodeuser();
        }
    }
}
