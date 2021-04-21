package com.example.mp07_statson.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Equipo {
    @PrimaryKey(autoGenerate = true) @NonNull
    public int idEquipo;
    public String nombreEquipo;
    public String imagen;


    public Equipo(String nombreEquipo, String imagen) {
        this.nombreEquipo = nombreEquipo;
        this.imagen = imagen;
    }

    public Equipo() {
    }
}
