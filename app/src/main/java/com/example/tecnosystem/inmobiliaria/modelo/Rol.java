package com.example.tecnosystem.inmobiliaria.modelo;

/**
 * Created by TecnoSystem on 4/11/2017.
 */

public class Rol {

    private Integer codigo;
    private String nombre;

    public Rol(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Rol() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
