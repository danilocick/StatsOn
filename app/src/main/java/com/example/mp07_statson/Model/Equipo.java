package com.example.mp07_statson.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipo {
    @PrimaryKey(autoGenerate = true) @NonNull
    int idEquipo;

    String nombreEquipo;

    public Equipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
}
