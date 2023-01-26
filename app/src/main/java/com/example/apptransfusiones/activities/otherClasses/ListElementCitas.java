package com.example.apptransfusiones.activities.otherClasses;

public class ListElementCitas {

    private String nombreHospital;
    private String direccionHospital;
    private String ciudadHospital;
    private String fecha;
    private String hora;

    public ListElementCitas() {}

    public ListElementCitas(String nombre, String direccion, String ciudad) {
        this.nombreHospital = nombre;
        this.direccionHospital = direccion;
        this.ciudadHospital = ciudad;
    }

    public String getNombreHospital() {
        return nombreHospital;
    }

    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
    }

    public String getDireccionHospital() {
        return direccionHospital;
    }

    public void setDireccionHospital(String direccionHospital) {
        this.direccionHospital = direccionHospital;
    }

    public String getCiudadHospital() {
        return ciudadHospital;
    }

    public void setCiudadHospital(String ciudadHospital) {
        this.ciudadHospital = ciudadHospital;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
