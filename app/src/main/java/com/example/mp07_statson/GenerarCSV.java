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

public class GenerarCSV extends BaseFragment {

    public void generarCSV(Partido partido, List<Jugador> jugadoresEquipoLocal, List<Jugador> jugadoresEquipoVisitante){

        String[] header = {"Dorsal", "Nombre", "Min", "PTS", "TL", "T2", "T3", "Rebotes", "Faltas", "Tapones", "Balones", "Tapones",
                "Pasos", "Dobles", "V.T", "As", "Val"};

        String[] header2 = {"", "", "", "", "", "TLA", "TLI", "TL%", "T2A", "T2I", "T2%", "T3A", "T3I", "T3%", "TOT", "DEF", "OF", "COM", "REC",
                "REC", "PER", "REC", "COM", "", "", "", "", ""};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(header2);

        for (int i = 0; i < jugadoresEquipoLocal.size(); i++) {
//            int tli=jugadoresEquipoLocal.get(i).t1mas+jugadoresEquipoLocal.get(i).t1menos;
//            int t2i=jugadoresEquipoLocal.get(i).t2mas+jugadoresEquipoLocal.get(i).t2menos;
//            int t3i=jugadoresEquipoLocal.get(i).t3mas+jugadoresEquipoLocal.get(i).t2menos;
//
//            int tlpor = tli / jugadoresEquipoLocal.get(i).t1mas;
//            int t2por = t2i / jugadoresEquipoLocal.get(i).t2mas;
//            int t3por = t3i / jugadoresEquipoLocal.get(i).t3mas;


            String[] jugador = {
                    jugadoresEquipoLocal.get(i).dorsal+"",
                    jugadoresEquipoLocal.get(i).nombre+"", "",
//                    jugadoresEquipoLocal.get(i).puntos+"",
//                    jugadoresEquipoLocal.get(i).t1mas+"",
//                    tli+"",
//                    tlpor+"",
//                    jugadoresEquipoLocal.get(i).t2mas+"",
//                    t2i+"",
//                    t2por+"",
//                    jugadoresEquipoLocal.get(i).t3mas+"",
//                    t3i+"",
//                    t3por+"",
//                    jugadoresEquipoLocal.get(i).rebotes+"",
//                    jugadoresEquipoLocal.get(i).rebotesDef+"",
//                    jugadoresEquipoLocal.get(i).rebotesOf+"",
//                    jugadoresEquipoLocal.get(i).faltasCometidas+"",
//                    jugadoresEquipoLocal.get(i).faltasRecibidas+"",
//                    jugadoresEquipoLocal.get(i).robos+"",
//                    jugadoresEquipoLocal.get(i).perdidas+"",
//                    jugadoresEquipoLocal.get(i).taponesRecibidos+"",
//                    jugadoresEquipoLocal.get(i).tapones+"",
//                    "","","",
//                    jugadoresEquipoLocal.get(i).asistencias+"",
                    ""};
            list.add(jugador);
        }

        // default all fields are enclosed in double quotes
        // default separator is a comma
        File s = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        try (CSVWriter writer = new CSVWriter(new FileWriter(s+"/partido.csv"))) {
            writer.writeAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
