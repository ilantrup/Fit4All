package com.example.fit4all;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Estiramiento {

    private String _idEstiramiento;
    private String _Foto;
    private String _Musculo;
    private Drawable _imagen;

    public void set_idEstiramiento(String _idEstiramiento) { this._idEstiramiento = _idEstiramiento; }

    public void set_Foto(String _Foto) {
        this._Foto = _Foto;
    }

    public void set_Musculo(String _Musculo) {
        this._Musculo = _Musculo;
    }

    public void set_imagen(Drawable _imagen) {
        this._imagen = _imagen;
    }

    public String get_Foto() {
        return _Foto;
    }

    public Drawable get_imagen() {
        return _imagen;
    }

    public String get_idEstiramiento() {
        return _idEstiramiento;
    }

    public String get_Musculo() {
        return _Musculo;
    }


    public Estiramiento(){
        this._Foto= "";
        this._idEstiramiento = "";
        this._imagen = null;
        this._Musculo = "";
    }

}
