package com.faltauno.faltauno;

/**
 * Created by Exequiel on 28/05/2017.
 * Clase para trabajar los elementos de Partido
 */

public class MiPartido {
    public String titulo;
    public String cancha;
    public String fecha;
    public String hora;
    public String rol;
    public int imagen;

    public MiPartido(String nombrePartido, String cancha,
                     String fechaPartido, int imagen, String horaPartido, String rolPartido){
        this.titulo = nombrePartido;
        this.cancha = cancha;
        fecha = fechaPartido;
        hora = horaPartido;
        rol = rolPartido;
        imagen= (int) (Math.random()*20)+18;
    }

    public String getNombrePartido() {
        return titulo;
    }
    public String getCancha() {
        return cancha;
    }
    public String getFechaPartido() {
        return fecha;
    }
    public String getHoraPartido() {
        return hora;
    }
    public String getRolPartido(){ return rol;}
/*    public int getImagen() {
        return mImagen;
    }
*/
    public void setNombrePartido(String mnombrePartido) {
        this.titulo =mnombrePartido;
    }

    public void setCancha(String cancha) {
        this.cancha =cancha;
    }

    public void setRolPartido(String rolPartido) {
        this.rol =rolPartido;
    }
    public void setFechaPartido(String fechaHoraPartido) {
        this.fecha =fechaHoraPartido;
    }
    public void setHoraPartido(String horaPartido) {
        this.fecha =horaPartido;
    }

/*    public void setImagen(int imagen) {
        this.mImagen=imagen;
    }
*/
/*
    public String toString() {
        return"Partido{"+
                "NombrePartido='"+ titulo +'\'' +
                ",JugadoresFaltantes='Faltan: "+ jugadoresFaltantes +" Jugadores"+'\'' +
                ",Cancha='"+ cancha +" - "+ fecha +'\'' +
                "CreadoPor='CreadoPor:"+ host + '\'' +
                '}';
    }*/
}
