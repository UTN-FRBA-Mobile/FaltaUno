package com.faltauno.faltauno;

import java.util.UUID;

/**
 * Created by Exequiel on 28/05/2017.
 * Clase para trabajar los elementos de Partido
 */

public class Partidos {
    private String mnombrePartido;
    private int mJugadoresFaltantes;
    private String mCancha;
    private String mCreadoPor;
    private String mFechaHoraPartido;
    private int mImagen;

    public Partidos (String nombrePartido, int jugadoresFaltantes, String cancha,
                     String creadoPor, String fechaHoraPartido, int imagen){
        mnombrePartido = nombrePartido;
        mJugadoresFaltantes= (int) (Math.random()*11);
        mCancha= cancha;
        mCreadoPor= creadoPor;
        mFechaHoraPartido= fechaHoraPartido;
        mImagen= (int) (Math.random()*20)+18;
    }



    public String getnombrePartido() {
        return mnombrePartido;
    }
    public int getJugadoresFaltantes() {
        return mJugadoresFaltantes;
    }
    public String getCancha() {
        return mCancha;
    }
    public String getCreadoPor() {
        return mCreadoPor;
    }
    public String getFechaHoraPartido() {
        return mFechaHoraPartido;
    }
    public int getImagen() {
        return mImagen;
    }

    public void setidPartido(String mnombrePartido) {
        this.mnombrePartido=mnombrePartido;
    }
    public void getJugadoresFaltantes(int jugadoresFaltantes) {
        this.mJugadoresFaltantes = jugadoresFaltantes;
    }
    public void getCancha(String cancha) {
        this.mCancha=cancha;
    }
    public void getCreadoPor(String creadoPor) {
        this.mCreadoPor=creadoPor;
    }
    public void getFechaHoraPartido(String fechaHoraPartido) {
        this.mFechaHoraPartido=fechaHoraPartido;
    }
    public void getImagen(int imagen) {
        this.mImagen=imagen;
    }

    public String toString() {
        return"Partidos{"+
                "NombrePartido='"+mnombrePartido+'\'' +
                ",JugadoresFaltantes='Faltan: "+mJugadoresFaltantes+" Jugadores"+'\'' +
                ",Cancha='"+mCancha+" - "+mFechaHoraPartido +'\'' +
                "CreadoPor='CreadoPor:"+mCreadoPor+ '\'' +
                '}';
    }
}
