package com.example.fit4all;

import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class fragDescanso extends Fragment implements View.OnClickListener {
    Button atras;
    ImageView img;
    Ejercicio sigEj;
    TextView txtSigEj,cron;
    long Start=10000,leftTime= Start;
    CountDownTimer countDown;
    MainActivity main;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_descanso, container, false);
        main = (MainActivity) getActivity();
        cron = vista.findViewById(R.id.cronometroDes);
        img = vista.findViewById(R.id.imagenSigEj);
        atras = vista.findViewById(R.id.atrasEJ);
        txtSigEj = vista.findViewById(R.id.SigEjer);
        atras.setOnClickListener(this);
        sigEj = main.devolverSigEj();
        txtSigEj.setText(sigEj.get_NombreEjercicio());
        //img.setImageDrawable(sigEj.get_imagen());
        if (!sigEj.get_Foto().equals("")) {
            Picasso.with(img.getContext()).load(sigEj.get_Foto()).into(img);
        }
        comenzar();
        return vista;
    }


     public void comenzar()
    {
        countDown= new CountDownTimer(leftTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime=millisUntilFinished;
                mostrarTiempo();
            }

            @Override
            public void onFinish() {
                countDown.cancel();
                if(!sigEj.get_rec())
                {
                    main.pasarASerieEjer();
                }else {
                    main.pasarFragEjcam();
                }

            }
        }.start();
    }
    public void mostrarTiempo()
    {
        int minutes = (int) (leftTime/1000)/60;
        int seconds = (int) (leftTime/1000)%60;
        String der= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        cron.setText(der);
    }
    @Override
    public void onClick(View vista) {
        Button botonApretado;
        botonApretado= (Button) vista;
        if(atras.getId()== botonApretado.getId())
        {
            countDown.cancel();
            main.iListaEj = 0;
            main.pasarANav();
        }
    }
}
