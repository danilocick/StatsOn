package com.example.mp07_statson.Model;

public class Accion {
    public boolean equipoLocal;
    public String jugada;
    public Jugador jugador;

    public Accion(boolean equipoLocal, String jugada, Jugador jugador) {
        this.equipoLocal = equipoLocal;
        this.jugada = jugada;
        this.jugador = jugador;
    }

    public Accion(Accion accion) {
        equipoLocal = accion.equipoLocal;
        jugada = accion.jugada;
        jugador=accion.jugador;
    }

    public Accion() {
    }

    @Override
    public String toString() {
        return "Accion{" +
                "equipoLocal=" + equipoLocal +
                ", accion='" + jugada + '\'' +
                ", jugador=" + jugador.dorsal +
                '}';
    }
}
