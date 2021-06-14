package com.example.mp07_statson.Model;

public class Accion {
    boolean equipoLocal;
    String accion;
    Jugador jugador;

    public Accion(boolean equipoLocal, String accion, Jugador jugador) {
        this.equipoLocal = equipoLocal;
        this.accion = accion;
        this.jugador = jugador;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "equipoLocal=" + equipoLocal +
                ", accion='" + accion + '\'' +
                ", jugador=" + jugador.dorsal +
                '}';
    }
}
