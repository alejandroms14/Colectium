package com.example.pokemon_firebase.menu.clases;

import java.io.Serializable;
import java.util.Objects;

public class Usuario implements Serializable {
    private String DNI,nombre,apellidos,localidad,direccion,correo;
    private int telefono;
    //constructor
    public Usuario(String DNI, String nombre, String apellidos, String localidad, String direccion, String correo, int telefono) {
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.localidad = localidad;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Usuario() {
    }
    //getters and setters
    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    //hashCode and equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return DNI.equals(usuario.DNI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(DNI);
    }

}
