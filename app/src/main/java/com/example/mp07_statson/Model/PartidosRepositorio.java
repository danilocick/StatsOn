package com.example.mp07_statson.Model;

import java.util.ArrayList;
import java.util.List;

public class PartidosRepositorio {
    List<Partido> partidos = new ArrayList<Partido>();

    public PartidosRepositorio(){
        partidos.add(new Partido("Santa Coloma", "Sant Josep"));
        partidos.add(new Partido("Santa Coloma", "Mina"));
        partidos.add(new Partido("Santa Coloma", "Barsa"));
        partidos.add(new Partido("Santa Coloma", "Peña"));
        partidos.add(new Partido("Santa Coloma", "Masnou"));
        partidos.add(new Partido("Santa Coloma", "Lugo"));
        partidos.add(new Partido("Santa Coloma", "Fuenlabrada"));
        partidos.add(new Partido("Santa Coloma", "Estudiantes"));
        partidos.add(new Partido("Santa Coloma", "Madrid"));
        partidos.add(new Partido("Santa Coloma", "Neus"));
        partidos.add(new Partido("Santa Coloma", "Fuster"));
    }

    public List<Partido> obtener(){return partidos;}

}

