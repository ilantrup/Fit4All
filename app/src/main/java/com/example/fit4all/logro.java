package com.example.fit4all;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.Date;

public class logro {
    private String _nombre;
    private Calendar _fecha;
    private Boolean _unblocked;
    private String _url;

    public void setunBlocked(Boolean tipo) {
        this._unblocked = tipo;
    }
    public void set_fecha(Calendar fech) {
        this._fecha = fech;
    }
    public void set_nombres(String nom) {
        this._nombre = nom;
    }
    public void set_url(String url) {
        this._url = url;
    }


    public Boolean getunBlocked() {
        return this._unblocked;
    }
    public Calendar get_fecha() {
        return this._fecha;
    }
    public String get_nombres() {
        return this._nombre;
    }
    public String get_url() {
        return this._url;
    }

    public logro() {}
}




