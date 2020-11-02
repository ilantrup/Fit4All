package com.example.fit4all;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class fragNavbar extends Fragment {

    private final static int ID_PERFIL = 1;
    private final static int ID_HOME = 2;
    private final static int ID_CALENDAR = 3;



    FragmentManager manager;
    FragmentTransaction transacFrag;
    FragmentManager manager2;
    FragmentTransaction transacFrag2;
    MainActivity main;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        main = (MainActivity) getActivity();
        View vista;
        vista = inflater.inflate(R.layout.layout_navbar, container, false);
        MeowBottomNavigation botnav = (MeowBottomNavigation) vista.findViewById(R.id.bottomNavigationBar);
        Log.d("BottomNav", "Arranco a poner los iconos");
        botnav.add(new MeowBottomNavigation.Model(ID_PERFIL, R.drawable.ic_person_black_24dp));
        botnav.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home_black_24dp));
        botnav.add(new MeowBottomNavigation.Model(ID_CALENDAR, R.drawable.ic_date_range_black_24dp));
        Log.d("BottomNav", "Termino de poner los iconos");

        if (!main.compararUltFecha()){
            main.reiniciarListaDeEjs();
            main.reiniciarListaDeEjsMedio();
            main.reiniciarBooleanos();
            main.reinciarZona();
        }





        botnav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {


                switch (item.getId()) {
                    case ID_HOME:
                        main.pasarAPrin();
                        break;
                    case ID_PERFIL:
                        main.pasarAPerfil();
                        break;
                    case ID_CALENDAR:
                        main.pasarAcalendario();
                        break;
                }
            }
        });
        botnav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case ID_HOME:
                        main.pasarAPrin();
                        break;
                    case ID_PERFIL:
                        main.pasarAPerfil();
                        break;
                    case ID_CALENDAR:
                        main.pasarAcalendario();
                        break;
                }
            }
        });

        botnav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {


                    switch (item.getId()) {
                        case ID_HOME:
                            main.pasarAPrin();
                            break;
                        case ID_PERFIL:
                            main.pasarAPerfil();
                            break;
                        case ID_CALENDAR:
                            main.pasarAcalendario();
                            break;
                    }
                }
        });



        main.pasarAPrin();

        return vista;


    }
}
