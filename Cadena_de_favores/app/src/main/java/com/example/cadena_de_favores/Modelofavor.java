package com.example.cadena_de_favores;

public class Modelofavor {
    private String Nombre,Fecha,Titulo,Descripcion;

    public Modelofavor(String nombre, String fecha, String titulo, String descripcion) {

        Nombre=nombre;
        Fecha=fecha;
        Titulo=titulo;
        Descripcion=descripcion;

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
}
