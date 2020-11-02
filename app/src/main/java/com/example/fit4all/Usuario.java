package com.example.fit4all;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    private String _idUsuario;
    private String _Nombre;
    private String _Apellido;
    private String _Sexo;
    private Date _Edad;
    private Double _Altura;
    private Double _Peso;
    private String _TipoAlimentacion;
    private String _idExperiencia;
    private Boolean _ModoLesion;
    private String _Foto;
    private String _Cita;
    private String _idCalendario;
    private List<String> _Logros;
    private Double _Dedicacion;
    private String _Objetivo;
    private Double _xpSuperior;
    private Double _xpInferior;
    private Double _xpMedio;



    //getters
    public String get_idUsuario(){return _idUsuario;}
    public String get_Nombre(){return _Nombre;}
    public String get_Apellido(){return _Apellido;}
    public String get_Sexo(){return _Sexo;}
    public Date get_Edad(){return _Edad;}
    public Double get_Altura(){return _Altura;}
    public Double get_Peso(){return _Peso;}
    public String get_TipoAlimentacion(){return _TipoAlimentacion;}
    public String get_idExperiencia(){return _idExperiencia;}
    public Boolean get_ModoLesion(){return _ModoLesion;}
    public String get_Foto(){return _Foto;}
    public String get_Cita(){return _Cita;}
    public String get_idCalendario(){return _idCalendario;}
    public List<String> get_Logros() {return _Logros; }
    public Double get_Dedicacion(){return _Dedicacion;}
    public String get_Objetivo() { return _Objetivo; }
    public Double get_xpInferior() { return _xpInferior; }
    public Double get_xpMedio() { return _xpMedio; }
    public Double get_xpSuperior() { return _xpSuperior; }


    //setters
    public void set_idUsuario(String newID){this._idUsuario = newID;}

    public void set_Apellido(String Apellido) {
        this._Apellido = Apellido;
    }

    public void set_Altura(Double _Altura) {
        this._Altura = _Altura;
    }

    public void set_Cita(String _Cita) {
        this._Cita = _Cita;
    }

    public void set_Edad(Date _Edad) {
        this._Edad = _Edad;
    }

    public void set_Foto(String _Foto) {
        this._Foto = _Foto;
    }

    public void set_idCalendario(String _idCalendario) {
        this._idCalendario = _idCalendario;
    }

    public void set_idExperiencia(String _idExperiencia) {
        this._idExperiencia = _idExperiencia;
    }

    public void set_ModoLesion(Boolean _ModoLesion) {
        this._ModoLesion = _ModoLesion;
    }

    public void set_Nombre(String _Nombre) {
        this._Nombre = _Nombre;
    }

    public void set_Peso(Double _Peso) {
        this._Peso = _Peso;
    }

    public void set_Sexo(String _Sexo) {
        this._Sexo = _Sexo;
    }

    public void set_TipoAlimentacion(String _TipoAlimentacion) { this._TipoAlimentacion = _TipoAlimentacion; }

    public void set_Logros(List<String> _Logros) { this._Logros = _Logros; }

    public void set_Dedicacion(Double _Dedicacion) { this._Dedicacion = _Dedicacion; }

    public void set_Objetivo(String _Objetivo) { this._Objetivo = _Objetivo; }

    public void set_xpInferior(Double _xpInferior) { this._xpInferior = _xpInferior; }

    public void set_xpMedio(Double _xpMedio) { this._xpMedio = _xpMedio; }

    public void set_xpSuperior(Double _xpSuperior) { this._xpSuperior = _xpSuperior; }

    //Constructor
    public Usuario(){
    this._idUsuario = "";
    this._Altura = 0.0;
    this._Apellido = "";
    this._Cita = "";
    this._Edad = null;
    this._Foto = "";
    this._idCalendario = "";
    this._idExperiencia = "";
    this._Logros = new ArrayList<>();
    this._ModoLesion = false;
    this._Nombre = "";
    this._Peso = 0.0;
    this._Sexo = "";
    this._TipoAlimentacion = "";
    this._Objetivo = "";
    this._Dedicacion = 0.0;
    this._xpInferior = 0.0;
    this._xpInferior = 0.0;
    this._xpInferior = 0.0;

    }
}


