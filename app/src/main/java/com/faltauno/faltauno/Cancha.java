package com.faltauno.faltauno;

/**
 * Created by Vane on 5/6/2017.
 */

public class Cancha {

    private String Unombre;
    private String Udireccion;
   // private String Uid;

    public Cancha() {
        // Needed for Firebase
    }

    public Cancha(String nombre, String direccion) {
        Unombre = nombre;
        Udireccion = direccion;
    }

    public String getName() {
        return Unombre;
    }

    public void setName(String nombre) {
        Unombre = nombre;
    }

    public String getDire() {
        return Udireccion;
    }

    public void setDire(String direccion) {
        Udireccion = direccion;
    }

//    public String getUid() {
//        return mUid;
//    }
//
//    public void setUid(String uid) {
//        mUid = uid;
//    }
}
