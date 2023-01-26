package com.example.apptransfusiones.activities.otherClasses;

public class Usuario {

    private String email;
    private String nombre;
    private String password;

    public Usuario() {}

    public Usuario(String n, String e, String p) {
        this.nombre = n;
        this.email = e;
        this.password = p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
