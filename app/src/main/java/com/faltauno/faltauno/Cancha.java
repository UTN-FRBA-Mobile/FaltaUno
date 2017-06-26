package com.faltauno.faltauno;

/**
 * Created by Vane on 5/6/2017.
 */

public class Cancha {

    public String nombre;
    public String direccion;
   // private String Uid;

    public Cancha() {
        // Needed for Firebase
    }

    public Cancha(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getName() {
        return nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getDire() {
        return direccion;
    }

    public void setDire(String direccion) {
        this.direccion = direccion;
    }

//    public String getUid() {
//        return mUid;
//    }
//
//    public void setUid(String uid) {
//        mUid = uid;
//    }
}
