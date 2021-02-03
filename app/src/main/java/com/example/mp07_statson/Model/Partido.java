package com.example.mp07_statson.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Partido {
    @PrimaryKey(autoGenerate = true)
    public int idPartido;
    public int idLocal;
    public int idVisitante;
    public int puntosLocal;
    public int puntosVisitante;

    public Partido(int idLocal, int idVisitante, int puntosLocal, int puntosVisitante) {
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }
}
