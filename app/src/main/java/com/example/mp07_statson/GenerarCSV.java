package com.example.mp07_statson;

import android.os.Environment;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerarCSV {

    public File generarCSV(Partido partido, List<Jugador> jugadoresEquipoLocal, List<Jugador> jugadoresEquipoVisitante) throws IOException {

        List<String[]> list = getContent(jugadoresEquipoLocal, jugadoresEquipoVisitante, partido);

        // default all fields are enclosed in double quotes
        // default separator is a comma
        File s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        File file = File.createTempFile("partido_"+partido.nombreEquipoLocal+"_"+partido.nombreEquipoVisitante, ".csv");
        FileWriter fw = new FileWriter(file);
        CSVWriter writer = new CSVWriter(fw);
        writer.writeAll(list);
        writer.close();

        return file;
    }

    private List<String[]> getContent(List<Jugador> jugadoresEquipoLocal, List<Jugador> jugadoresEquipoVisitante, Partido partido) {
        String[] header = {"Dorsal", "Nombre", "Min", "PTS", "TL", "","","T2", "","","T3","","", "Rebotes","","", "Faltas","", "Balones","", "Tapones","",
                "Pasos", "Dobles", "V.T", "As", "Val"};

        String[] header2 = {"", "", "", "", "TLA", "TLI", "TL%", "T2A", "T2I", "T2%", "T3A", "T3I", "T3%", "TOT", "DEF", "OF", "COM", "REC",
                "REC", "PER", "REC", "COM", "", "", "", "", ""};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(header2);
        String[] s = {"",""};
        for (int i = 0; i < jugadoresEquipoLocal.size(); i++) {
//            int tli= jugadoresEquipoLocal.get(i).t1mas+ jugadoresEquipoLocal.get(i).t1menos;
//            int t2i= jugadoresEquipoLocal.get(i).t2mas+ jugadoresEquipoLocal.get(i).t2menos;
//            int t3i= jugadoresEquipoLocal.get(i).t3mas+ jugadoresEquipoLocal.get(i).t2menos;
//
//            int tlpor = tli / jugadoresEquipoLocal.get(i).t1mas;
//            int t2por = t2i / jugadoresEquipoLocal.get(i).t2mas;
//            int t3por = t3i / jugadoresEquipoLocal.get(i).t3mas;


            String[] jugador = {
                    String.valueOf(jugadoresEquipoLocal.get(i).dorsal),
                    jugadoresEquipoLocal.get(i).nombre, "",
                    String.valueOf(jugadoresEquipoLocal.get(i).puntos),
                    String.valueOf(jugadoresEquipoLocal.get(i).t1mas),"","",
//                    tli+"",
//                    tlpor+"",
                    String.valueOf(jugadoresEquipoLocal.get(i).t2mas),"","",
//                    t2i+"",
//                    t2por+"",
                    String.valueOf(jugadoresEquipoLocal.get(i).t3mas),"","",
//                    t3i+"",
//                    t3por+"",
                    String.valueOf(jugadoresEquipoLocal.get(i).rebotes),
                    String.valueOf(jugadoresEquipoLocal.get(i).rebotesDef),
                    String.valueOf(jugadoresEquipoLocal.get(i).rebotesOf),
                    String.valueOf(jugadoresEquipoLocal.get(i).faltasCometidas),
                    String.valueOf(jugadoresEquipoLocal.get(i).faltasRecibidas),
                    String.valueOf(jugadoresEquipoLocal.get(i).robos),
                    String.valueOf(jugadoresEquipoLocal.get(i).perdidas),
                    String.valueOf(jugadoresEquipoLocal.get(i).taponesRecibidos),
                    String.valueOf(jugadoresEquipoLocal.get(i).tapones),
                    "","","",
                    String.valueOf(jugadoresEquipoLocal.get(i).asistencias),
                    ""};
            list.add(jugador);
        }
        String[] equipoLocal = {
                "","Equipo","",
                String.valueOf(partido.puntosLocal),
                String.valueOf(partido.t1masLocal), "","",
                String.valueOf(partido.t2masLocal),"","",
                String.valueOf(partido.t3masLocal),"","",
                String.valueOf(partido.rebotesLocal),
                String.valueOf(partido.rebotesDefLocal),
                String.valueOf(partido.rebotesOfLocal),
                String.valueOf(partido.faltasCometidasLocal),
                String.valueOf(partido.faltasRecibidasLocal),
                String.valueOf(partido.robosLocal),
                String.valueOf(partido.perdidasLocal),
                String.valueOf(partido.taponesRecibidosLocal),
                String.valueOf(partido.taponesLocal), "","","",
                String.valueOf(partido.asistenciasLocal),"" };

        list.add(equipoLocal);
        list.add(s);
        list.add(s);

        String[] cuartos = { "","1 periodo", "2 periodo", "3 periodo", "4 periodo"};
        list.add(cuartos);

        String[] puntosLocal = {String.valueOf(partido.nombreEquipoLocal),String.valueOf(partido.puntosLocalPrimerCuarto), String.valueOf(partido.puntosLocalSegundoCuarto),String.valueOf(partido.puntosLocalTercerCuarto), String.valueOf(partido.puntosLocalQuartoCuarto)};
        String[] puntosVisitante = {String.valueOf(partido.nombreEquipoVisitante),String.valueOf(partido.puntosVisitantePrimerCuarto), String.valueOf(partido.puntosVisitanteSegundoCuarto),String.valueOf(partido.puntosVisitenteTercerCuarto), String.valueOf(partido.puntosVisitanteQuartoCuarto)};
        list.add(puntosLocal);
        list.add(puntosVisitante);

        list.add(s);
        list.add(s);

        list.add(header);
        list.add(header2);
        for (int i = 0; i < jugadoresEquipoVisitante.size(); i++) {
//            int tli=jugadoresEquipoVisitante.get(i).t1mas+jugadoresEquipoVisitante.get(i).t1menos;
//            int t2i=jugadoresEquipoVisitante.get(i).t2mas+jugadoresEquipoVisitante.get(i).t2menos;
//            int t3i=jugadoresEquipoVisitante.get(i).t3mas+jugadoresEquipoVisitante.get(i).t2menos;
//
//            int tlpor = tli / jugadoresEquipoVisitante.get(i).t1mas;
//            int t2por = t2i / jugadoresEquipoVisitante.get(i).t2mas;
//            int t3por = t3i / jugadoresEquipoVisitante.get(i).t3mas;


            String[] jugador = {
                    String.valueOf(jugadoresEquipoVisitante.get(i).dorsal),
                    jugadoresEquipoVisitante.get(i).nombre, "",
                    String.valueOf(jugadoresEquipoVisitante.get(i).puntos),
                    String.valueOf(jugadoresEquipoVisitante.get(i).t1mas),"","",
//                    tli+"",
//                    tlpor+"",
                    String.valueOf(jugadoresEquipoVisitante.get(i).t2mas),"","",
//                    t2i+"",
//                    t2por+"",
                    String.valueOf(jugadoresEquipoVisitante.get(i).t3mas),"","",
//                    t3i+"",
//                    t3por+"",
                    String.valueOf(jugadoresEquipoVisitante.get(i).rebotes),
                    String.valueOf(jugadoresEquipoVisitante.get(i).rebotesDef),
                    String.valueOf(jugadoresEquipoVisitante.get(i).rebotesOf),
                    String.valueOf(jugadoresEquipoVisitante.get(i).faltasCometidas),
                    String.valueOf(jugadoresEquipoVisitante.get(i).faltasRecibidas),
                    String.valueOf(jugadoresEquipoVisitante.get(i).robos),
                    String.valueOf(jugadoresEquipoVisitante.get(i).perdidas),
                    String.valueOf(jugadoresEquipoVisitante.get(i).taponesRecibidos),
                    String.valueOf(jugadoresEquipoVisitante.get(i).tapones),
                    "","","",
                    String.valueOf(jugadoresEquipoVisitante.get(i).asistencias),
                    ""};
            list.add(jugador);
        }
        String[] equipoVisitante = {
                "","Equipo","",
                String.valueOf(partido.puntosVisitante),
                String.valueOf(partido.t1masVisitante), "","",
                String.valueOf(partido.t2masVisitante),"","",
                String.valueOf(partido.t3masVisitante),"","",
                String.valueOf(partido.rebotesVisitante),
                String.valueOf(partido.rebotesDefVisitante),
                String.valueOf(partido.rebotesOfVisitante),
                String.valueOf(partido.faltasCometidasVisitante),
                String.valueOf(partido.faltasRecibidasVisitante),
                String.valueOf(partido.robosVisitante),
                String.valueOf(partido.perdidasVisitante),
                String.valueOf(partido.taponesRecibidosVisitante),
                String.valueOf(partido.taponesVisitante), "","","",
                String.valueOf(partido.asistenciasVisitante),"" };
        list.add(equipoVisitante);

        return list;
    }
}
