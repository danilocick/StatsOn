package com.example.mp07_statson.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipo {
    @PrimaryKey @NonNull
        int idEquipo;

    String nombreEquipo;

    public Equipo(int idEquipo, String nombreEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }
}
