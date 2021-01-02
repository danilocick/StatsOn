package com.example.mp07_statson;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Jugador {
    @PrimaryKey @NonNull
        String dorsal = "0";

    String nombre;
    String imagen;
    int puntos, rebotes, asistencias, recuperaciones, perdidas, tapones;
    int t1mas, t1menos, t2mas, t2menos, t3mas, t3menos;
    int rebotesDef, rebotesOf;
    int faltasRecibidas, faltasCometidas;
    int taponesRealizados, taponesRecibidos;

    public Jugador(String nombre, String dorsal, String imagen) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.imagen = imagen;
        puntos= 0;
        rebotes= 0;
        asistencias= 0;
        recuperaciones= 0;
        perdidas= 0;
        tapones= 0;
        t1mas= 0;
        t1menos= 0;
        t2mas= 0;
        t2menos= 0;
        t3mas= 0;
        t3menos= 0;
        rebotesDef= 0;
        rebotesOf= 0;
        faltasRecibidas= 0;
        faltasCometidas= 0;
        taponesRealizados= 0;
        taponesRecibidos= 0;
    }
}
