package com.example.mp07_statson;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerarCSV extends BaseFragment {

    public void generarCSV(){

        String[] header = {"Dorsal", "Nombre", "Min", "PTS", "TL", "T2", "T3", "Rebotes", "Faltas", "Tapones", "Balones", "Tapones",
                "Pasos", "Dobles", "V.T", "As", "Val"};

        String[] header2 = {"", "", "", "", "", "TLA", "TLI", "TL%", "T2A", "T2I", "T2%", "T3A", "T3I", "T3%", "TOT", "DEF", "OF", "COM", "REC",
                "REC", "PER", "REC", "COM", "", "", "", "", ""};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(header2);

        for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
//            int tli=partidoviewmodel.jugadoresEquipoLocal.get(i).t1mas+partidoviewmodel.jugadoresEquipoLocal.get(i).t1menos;
//            int t2i=partidoviewmodel.jugadoresEquipoLocal.get(i).t2mas+partidoviewmodel.jugadoresEquipoLocal.get(i).t2menos;
//            int t3i=partidoviewmodel.jugadoresEquipoLocal.get(i).t3mas+partidoviewmodel.jugadoresEquipoLocal.get(i).t2menos;
//
//            int tlpor = tli / partidoviewmodel.jugadoresEquipoLocal.get(i).t1mas;
//            int t2por = t2i / partidoviewmodel.jugadoresEquipoLocal.get(i).t2mas;
//            int t3por = t3i / partidoviewmodel.jugadoresEquipoLocal.get(i).t3mas;


            String[] jugador = {partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal+"",
                    partidoviewmodel.jugadoresEquipoLocal.get(i).nombre+"", "",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).puntos+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).t1mas+"",
//                    tli+"",
//                    tlpor+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).t2mas+"",
//                    t2i+"",
//                    t2por+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).t3mas+"",
//                    t3i+"",
//                    t3por+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).rebotes+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).rebotesDef+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).rebotesOf+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).faltasRecibidas+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).robos+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).perdidas+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).taponesRecibidos+"",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).tapones+"",
//                    "","","",
//                    partidoviewmodel.jugadoresEquipoLocal.get(i).asistencias+"",
                    ""};
            list.add(jugador);
        }

        // default all fields are enclosed in double quotes
        // default separator is a comma
        try (CSVWriter writer = new CSVWriter(new FileWriter("partido.csv"))) {
            writer.writeAll(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
