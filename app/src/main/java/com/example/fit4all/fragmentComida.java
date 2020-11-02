package com.example.fit4all;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
public class fragmentComida extends Fragment implements View.OnClickListener {
    FragmentManager managerCom;
    FragmentTransaction transacFragCom;
    Button almuerzo,desayuno,cena;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_comida, container, false);
        almuerzo = vista.findViewById(R.id.comidaAlmuerzo);
        desayuno = vista.findViewById(R.id.comidaDesayuno);
        cena = vista.findViewById(R.id.comidaCena);
        almuerzo.setOnClickListener(this);
        cena.setOnClickListener(this);
        desayuno.setOnClickListener(this);
        managerCom = getFragmentManager();
        Fragment fragListaDes;
        fragListaDes = new fragListaComidaDesayuno();
        transacFragCom = managerCom.beginTransaction();
        transacFragCom.replace(R.id.frameHolderComida, fragListaDes);
        transacFragCom.commit();
        return vista;
    }

    public void onClick(View vista) {
        Button botonApretado;
        botonApretado = (Button) vista;

        if (almuerzo.getId() == botonApretado.getId()) {
            almuerzo.setBackgroundResource(R.drawable.cuadrado_ok_comida);
            cena.setBackgroundResource(R.color.White);
            desayuno.setBackgroundResource(R.color.White);
            Fragment fragListaAlm;
            fragListaAlm = new fragListaComidaAlmuerzo();
            transacFragCom = managerCom.beginTransaction();
            transacFragCom.replace(R.id.frameHolderComida, fragListaAlm);
            transacFragCom.commit();
        }
        if (desayuno.getId() == botonApretado.getId()) {
            desayuno.setBackgroundResource(R.drawable.cuadrado_ok_comida);
            cena.setBackgroundResource(R.color.White);
            almuerzo.setBackgroundResource(R.color.White);
            Fragment fragListaDes;
            fragListaDes = new fragListaComidaDesayuno();
            transacFragCom = managerCom.beginTransaction();
            transacFragCom.replace(R.id.frameHolderComida, fragListaDes);
            transacFragCom.commit();
        }
        if (cena.getId() == botonApretado.getId()) {
            cena.setBackgroundResource(R.drawable.cuadrado_ok_comida);
            desayuno.setBackgroundResource(R.color.White);
            almuerzo.setBackgroundResource(R.color.White);
            Fragment fragListaCen;
            fragListaCen = new fragListaComidaCena();
            transacFragCom = managerCom.beginTransaction();
            transacFragCom.replace(R.id.frameHolderComida, fragListaCen);
            transacFragCom.commit();
        }

    }
}
