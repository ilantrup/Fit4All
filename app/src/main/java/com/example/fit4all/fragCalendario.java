package com.example.fit4all;

import android.app.Fragment;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class fragCalendario extends Fragment {
    com.applandeo.materialcalendarview.CalendarView calendarView;
    MainActivity main;
    ArrayList<tipoEvento> tipEV;
    ArrayList<EventDay> events = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista;
        vista = inflater.inflate(R.layout.layout_calendario, container, false);
        calendarView = (CalendarView) vista.findViewById(R.id.calendarView);
        main = (MainActivity) getActivity();
        tipEV=main.devolverArrayCal();

        for(int i = 0; i < tipEV.size(); ++i)
        {
            java.util.Calendar calendar =tipEV.get(i).getDate();
            calendar.add(calendar.DAY_OF_MONTH,0);
            events.add(new EventDay(calendar, R.drawable.circ_rutina));
        }
        calendarView.setEvents(events);
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {
                Calendar mesa = calendarView.getCurrentPageDate();
                Log.d("MESA", String.valueOf(mesa.get(Calendar.MONTH)));
                Log.d("MESA", String.valueOf(mesa.get(Calendar.YEAR)));


            }
        });
        return vista;
    }

}







