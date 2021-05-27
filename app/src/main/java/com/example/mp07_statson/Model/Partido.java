package com.example.mp07_statson.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Partido {
    @PrimaryKey(autoGenerate = true)
    public String idPartido;
    public String archivoCSV;
    public String nombreEquipoLocal;
    public String nombreEquipoVisitante;

    public String imagenEquipoLocal;
    public String imagenEquipoVisitante;

    public String idEquipoLocal;
    public String idEquipoVisitante;
    public Date fecha;

    public int puntosLocal, rebotesLocal, asistenciasLocal, robosLocal, perdidasLocal,
            t1masLocal, t1menosLocal, t2masLocal, t2menosLocal, t3masLocal, t3menosLocal,
            rebotesDefLocal, rebotesOfLocal, faltasRecibidasLocal, faltasCometidasLocal,
            taponesLocal, taponesRecibidosLocal;

    public int puntosVisitante, rebotesVisitante, asistenciasVisitante, robosVisitante, perdidasVisitante,
            t1masVisitante, t1menosVisitante, t2masVisitante, t2menosVisitante, t3masVisitante, t3menosVisitante,
            rebotesDefVisitante, rebotesOfVisitante, faltasRecibidasVisitante, faltasCometidasVisitante,
            taponesVisitante, taponesRecibidosVisitante;

    public int puntosLocalPrimerCuarto,puntosLocalSegundoCuarto,puntosLocalTercerCuarto,puntosLocalQuartoCuarto;
    public int puntosVisitantePrimerCuarto,puntosVisitanteSegundoCuarto, puntosVisitanteTercerCuarto,puntosVisitanteQuartoCuarto;

    public Partido(String idLocal, String idVisitante, int puntosLocal, int puntosVisitante) {
        this.nombreEquipoLocal = idLocal;
        this.nombreEquipoVisitante = idVisitante;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public Partido() {
    }
}