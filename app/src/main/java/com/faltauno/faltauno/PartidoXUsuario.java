package com.faltauno.faltauno;

/**
 * Created by Chechu on 2/7/2017.
 */

public class PartidoXUsuario {
    private String partido;
    private String tipo;

    public PartidoXUsuario(String nombrePartido, String tipo) {
        this.partido = nombrePartido;
        this.tipo = tipo;

    };
    public String getNombrePartido() {
        return partido;
    }
    public String getTipo() {
        return tipo;
    }
}

