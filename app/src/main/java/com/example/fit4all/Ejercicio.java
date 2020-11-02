package com.example.fit4all;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Ejercicio {
    private String _idEjercicio;
    private String _Destreza;
    private Double _Dificultad;
    private String _Foto;
    private List<String> _Musculos;
    private String _NombreEjercicio;
    private Drawable _imagen;
    private Double _segundos;

    public void setIdEjercicio(String _idEjercicio) {
        this._idEjercicio = _idEjercicio;
    }

    public void set_Foto(String _Foto) {
        this._Foto = _Foto;
    }

    public void set_Destreza(String _Destreza) {
        this._Destreza = _Destreza;
    }

    public void set_Dificultad(Double _Dificultad) {
        this._Dificultad = _Dificultad;
    }

    public void set_Musculos(List<String> _Musculos) {
        this._Musculos = _Musculos;
    }

    public void set_img(Drawable img) {
        this._imagen = img;
    }

    public void set_NombreEjercicio(String _NombreEjercicio) {
        this._NombreEjercicio = _NombreEjercicio;
    }

    public void set_seg(Double seg) {
        this._segundos=seg;
    }

    public String getIdEjercicio() {
        return _idEjercicio;
    }

    public Double get_Dificultad() {
        return _Dificultad;
    }

    public List<String> get_Musculos() {
        return _Musculos;
    }

    public String get_Destreza() {
        return _Destreza;
    }

    public Drawable get_imagen() {
        return _imagen;
    }

    public Double get_Seg() {
        return _segundos;
    }

    public String get_NombreEjercicio() {
        return _NombreEjercicio;
    }

    public String get_Foto() { return _Foto; }

    public Ejercicio(){
        this._idEjercicio = "";
        this._Destreza = "";
        this._Dificultad = 0.0;
        this._Foto = "";
        this._Musculos = new ArrayList<>();
        this._NombreEjercicio = "";
        this._segundos=0.0;
    }


}
