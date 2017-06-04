package com.faltauno.faltauno;

/**
 * Created by Chechu on 25/5/2017.
 */

public class Canchas {
    public String nombre;
    public String ubicacion;
    public String barrio;
    public int imageId;

    Canchas(String nombre, String ubicacion, String barrio, int imageId) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.barrio = barrio;
        this.imageId = imageId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getImageId() {
        return imageId;
    }

    public String getBarrio() {
        return barrio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
