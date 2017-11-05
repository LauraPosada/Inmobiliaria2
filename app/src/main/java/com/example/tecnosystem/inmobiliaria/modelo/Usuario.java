package com.example.tecnosystem.inmobiliaria.modelo;

/**
 * Created by TecnoSystem on 2/11/2017.
 */

public class Usuario {
    private String nombreUsuario;
    private String pasword;
    private Persona Persona_cedula;
    private Rol rol_codigo;

    public Usuario(String nombreUsuario, String pasword, Persona persona_cedula, Rol rol_codigo) {
        this.nombreUsuario = nombreUsuario;
        this.pasword = pasword;
        Persona_cedula = persona_cedula;
        this.rol_codigo = rol_codigo;
    }

    public Usuario() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public Persona getPersona_cedula() {
        return Persona_cedula;
    }

    public void setPersona_cedula(Persona persona_cedula) {
        Persona_cedula = persona_cedula;
    }

    public Rol getRol_codigo() {
        return rol_codigo;
    }

    public void setRol_codigo(Rol rol_codigo) {
        this.rol_codigo = rol_codigo;
    }
}
