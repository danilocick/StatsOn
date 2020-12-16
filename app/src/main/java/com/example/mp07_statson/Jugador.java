package com.example.mp07_statson;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Jugador {
    @PrimaryKey(autoGenerate = true)
            int id;

    String nombre;
    String dorsal;
    String imagen;

    public Jugador(String nombre, String dorsal, String imagen) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.imagen = imagen;
    }
}
