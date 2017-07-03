package com.faltauno.faltauno;

import android.app.Application;

/**
 * Created by jonathan on 29/06/17.
 */

public class Usuario {
    private String nombre;
    private String usuario;
    private String fotoUrl;
//    private String userId;

    public Usuario() {}

    public Usuario(String nombre, String usuario) {
//        this.userId = userId;
        this.nombre = nombre;
        this.usuario = usuario;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

//    public String getUserId() {
//        return userId;
//    }

//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

//    private static Usuario instance;
//    public static synchronized Usuario getInstance() {
//        if (instance == null ) {
//            instance = new Usuario();
//        }
//        return instance;
//    }
}

