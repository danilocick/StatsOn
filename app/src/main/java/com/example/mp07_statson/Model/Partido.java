package com.example.mp07_statson.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Partido {
    @PrimaryKey(autoGenerate = true)
    public int idPartido;
    public int nombreEquipoLocal;
    public int nombreEquipoVisitante;

    public int puntosLocal, rebotesLocal, asistenciasLocal, robosLocal, perdidasLocal,
            t1masLocal, t1menosLocal, t2masLocal, t2menosLocal, t3masLocal, t3menosLocal,
            rebotesDefLocal, rebotesOfLocal, faltasRecibidasLocal, faltasCometidasLocal,
            taponesRealizadosLocal, taponesRecibidosLocal;

    public int puntosVisitante, rebotesVisitante, asistenciasVisitante, robosVisitante, perdidasVisitante,
            t1masVisitante, t1menosVisitante, t2masVisitante, t2menosVisitante, t3masVisitante, t3menosVisitante,
            rebotesDefVisitante, rebotesOfVisitante, faltasRecibidasVisitante, faltasCometidasVisitante,
            taponesRealizadosVisitante, taponesRecibidosVisitante;

    public Partido(int idLocal, int idVisitante, int puntosLocal, int puntosVisitante) {
        this.nombreEquipoLocal = idLocal;
        this.nombreEquipoVisitante = idVisitante;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public Partido() {
    }
}
