package com.example.pokemon_firebase.menu.clases;

import java.io.Serializable;
import java.util.Objects;

public class  Pokemon implements Serializable {

    private String idPokemon;
    private String nombrePokemon;
    private String ataque;
    private String defensa;
    private String debilidad;
    private String foto;

    public Pokemon(String idPokemon, String nombrePokemon, String ataque, String defensa, String debilidad) {
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
        this.ataque = ataque;
        this.defensa = defensa;
        this.debilidad = debilidad;
    }

    public Pokemon(String idPokemon, String nombrePokemon, String ataque, String defensa, String debilidad, String foto) {
        this.idPokemon = idPokemon;
        this.nombrePokemon = nombrePokemon;
        this.ataque = ataque;
        this.defensa = defensa;
        this.debilidad = debilidad;
        this.foto = foto;
    }

    /*public Pokemon(String nombrePokemon, String ataque, String defensa, String debilidad, String foto) {
        this.nombrePokemon = nombrePokemon;
        this.ataque = ataque;
        this.defensa = defensa;
        this.debilidad = debilidad;
        this.foto = foto;
    }*/
    public Pokemon(String nombrePokemon, String ataque, String defensa, String debilidad) {
        this.nombrePokemon = nombrePokemon;
        this.ataque = ataque;
        this.defensa = defensa;
        this.debilidad = debilidad;
    }

    public Pokemon() {
        this.nombrePokemon = "";
        this.ataque = "";
        this.defensa = "";
        this.debilidad = "";
    }

    public String getIdPokemon() {
        return idPokemon;
    }

    public void setIdPokemon(String idPokemon) {
        this.idPokemon = idPokemon;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public String getAtaque() {
        return ataque;
    }

    public void setAtaque(String ataque) {
        this.ataque = ataque;
    }

    public String getDefensa() {
        return defensa;
    }

    public void setDefensa(String defensa) {
        this.defensa = defensa;
    }

    public String getDebilidad() {
        return debilidad;
    }

    public void setDebilidad(String debilidad) {
        this.debilidad = debilidad;
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
        Pokemon pokemon = (Pokemon) o;
        return idPokemon == pokemon.idPokemon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPokemon);
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "idPokemon=" + idPokemon +
                ", nombrePokemon='" + nombrePokemon + '\'' +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                ", debilidad=" + debilidad +
                '}';
    }
}
