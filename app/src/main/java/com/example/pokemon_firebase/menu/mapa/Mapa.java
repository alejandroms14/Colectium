package com.example.pokemon_firebase.menu.mapa;

import java.io.Serializable;

public class Mapa implements Serializable {
    private String nombre, foto;
    private double latidud, longitud;

    public Mapa() {

    }

    public Mapa(String nombre, String foto, double latidud, double longitud) {
        this.nombre = nombre;
        this.foto = foto;
        this.latidud = latidud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getLatidud() {
        return latidud;
    }

    public void setLatidud(double latidud) {
        this.latidud = latidud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
