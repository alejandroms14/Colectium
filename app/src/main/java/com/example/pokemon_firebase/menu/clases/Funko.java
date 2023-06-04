package com.example.pokemon_firebase.menu.clases;

import java.io.Serializable;
import java.util.Objects;

public class Funko implements Serializable {

    private String idFunko;
    private String nombreFunko;
    private String Categoria;
    private String foto;

    public Funko(String idFunko, String nombreFunko, String categoria, String foto) {
        this.idFunko = idFunko;
        this.nombreFunko = nombreFunko;
        Categoria = categoria;
        this.foto = foto;
    }

    public Funko(String idFunko, String nombreFunko, String categoria) {
        this.idFunko = idFunko;
        this.nombreFunko = nombreFunko;
        Categoria = categoria;

    }

    public Funko() {
        this.idFunko = "";
        this.nombreFunko = "";
        Categoria = "";
        this.foto = "";
    }

    public String getIdFunko() {
        return idFunko;
    }

    public void setIdFunko(String idFunko) {
        this.idFunko = idFunko;
    }

    public String getNombreFunko() {
        return nombreFunko;
    }

    public void setNombreFunko(String nombreFunko) {
        this.nombreFunko = nombreFunko;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funko funko = (Funko) o;
        return Objects.equals(idFunko, funko.idFunko);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFunko);
    }

    @Override
    public String toString() {
        return "Funko{" +
                "idFunko='" + idFunko + '\'' +
                ", nombreFunko='" + nombreFunko + '\'' +
                ", Categoria='" + Categoria + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}
