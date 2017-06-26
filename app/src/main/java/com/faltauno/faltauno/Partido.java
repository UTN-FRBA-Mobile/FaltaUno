package com.faltauno.faltauno;

/**
 * Created by Exequiel on 28/05/2017.
 * Clase para trabajar los elementos de Partido
 */

public class Partido {
    public String titulo;
    public int jugadoresFaltantes;
    public String cancha;
    public String host;
    public String fecha;
    public String hora;
    public int mImagen;

    public Partido(String nombrePartido, int jugadoresFaltantes, String cancha,
                   String creadoPor, String fechaPartido, int imagen, String horaPartido){
        this.titulo = nombrePartido;
        this.jugadoresFaltantes = jugadoresFaltantes;
        this.cancha = cancha;
        host = creadoPor;
        fecha = fechaPartido;
        hora = horaPartido;
        mImagen= (int) (Math.random()*20)+18;
    }



    public String getnombrePartido() {
        return titulo;
    }
    public int getJugadoresFaltantes() {
        return jugadoresFaltantes;
    }
    public String getCancha() {
        return cancha;
    }
    public String getCreadoPor() {
        return host;
    }
    public String getFechaPartido() {
        return fecha;
    }
    public String getHoraPartido() {
        return hora;
    }
    public int getImagen() {
        return mImagen;
    }

    public void setidPartido(String mnombrePartido) {
        this.titulo =mnombrePartido;
    }
    public void getJugadoresFaltantes(int jugadoresFaltantes) {
        this.jugadoresFaltantes = jugadoresFaltantes;
    }
    public void getCancha(String cancha) {
        this.cancha =cancha;
    }
    public void getCreadoPor(String creadoPor) {
        this.host =creadoPor;
    }
    public void getFechaHoraPartido(String fechaHoraPartido) {
        this.fecha =fechaHoraPartido;
    }
    public void getImagen(int imagen) {
        this.mImagen=imagen;
    }

    public String toString() {
        return"Partido{"+
                "NombrePartido='"+ titulo +'\'' +
                ",JugadoresFaltantes='Faltan: "+ jugadoresFaltantes +" Jugadores"+'\'' +
                ",Cancha='"+ cancha +" - "+ fecha +'\'' +
                "CreadoPor='CreadoPor:"+ host + '\'' +
                '}';
    }
}
