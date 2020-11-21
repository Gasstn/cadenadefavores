package com.example.cadena_de_favores;

public class Modelofavor {
    private String Nombre,Fecha,Titulo,Descripcion,Nro_tel;
    public Modelofavor(String nombre, String fecha, String titulo, String descripcion, String nro_tel) {
        Nombre=nombre;
        Fecha=fecha;
        Titulo=titulo;
        Descripcion=descripcion;
        Nro_tel=nro_tel;
    }
    public Modelofavor(String favor){
        Descripcion=favor;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        this.Fecha = fecha;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        this.Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public String getNro_tel() {
        return Nro_tel;
    }

    public void setNro_tel(String nro_tel) {
        Nro_tel = nro_tel;
    }
}
