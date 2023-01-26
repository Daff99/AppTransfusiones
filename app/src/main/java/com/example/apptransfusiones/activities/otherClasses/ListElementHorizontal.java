package com.example.apptransfusiones.activities.otherClasses;

public class ListElementHorizontal {

    private String texto;
    private int imagen;

    public ListElementHorizontal() { }

    public ListElementHorizontal(String texto, int imagen) {
        this.texto = texto;
        this.imagen = imagen;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
