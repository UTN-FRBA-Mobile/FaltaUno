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
    public int imagen;

    public Partido(String nombrePartido, int jugadoresFaltantes, String cancha,
                   String creadoPor, String fechaPartido, int imagen, String horaPartido){
        this.titulo = nombrePartido;
        this.jugadoresFaltantes = jugadoresFaltantes;
        this.cancha = cancha;
        host = creadoPor;
        fecha = fechaPartido;
        hora = horaPartido;
        imagen= (int) (Math.random()*20)+18;
    }
/*

    public String getNombrePartido() {
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

    public void setNombrePartido(String mnombrePartido) {
        this.titulo =mnombrePartido;
    }

    public void setJugadoresFaltantes(int jugadoresFaltantes) {
        this.jugadoresFaltantes = jugadoresFaltantes;
    }
    public void setCancha(String cancha) {
        this.cancha =cancha;
    }

    public void setCreadoPor(String creadoPor) {
        this.host =creadoPor;
    }
    public void setFechaPartido(String fechaHoraPartido) {
        this.fecha =fechaHoraPartido;
    }
    public void setHoraPartido(String horaPartido) {
        this.fecha =horaPartido;
    }
    public void setImagen(int imagen) {
        this.mImagen=imagen;
    }
*/

    public String toString() {
        return"Partido{"+
                "NombrePartido='"+ titulo +'\'' +
                ",JugadoresFaltantes='Faltan: "+ jugadoresFaltantes +" Jugadores"+'\'' +
                ",Cancha='"+ cancha +" - "+ fecha +'\'' +
                "CreadoPor='CreadoPor:"+ host + '\'' +
                '}';
    }
}
