package com.faltauno.faltauno;

/**
 * Created by Vane on 5/6/2017.
 */

public class Cancha {

    private String nombre;
    private String direccion;
    private int imagen;
   // private String Uid;

    public Cancha() {
        // Needed for Firebase
    }

    public Cancha(String nombre, String direccion, int imagen) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.imagen=imagen;
    }

  public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
   }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
