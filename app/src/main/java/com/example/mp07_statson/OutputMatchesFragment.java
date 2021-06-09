package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.ViewModel.PartidoViewModel;
import com.example.mp07_statson.ViewModel.StatsOnViewModel;
import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;

import java.util.List;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class OutputMatchesFragment extends DialogFragment {

    private FragmentOutputMatchesBinding binding;
    private PartidoViewModel partidoviewmodel;
    private StatsOnViewModel viewmodel;

    private int contador = 0;

    private final String[] header = {"Dorsal ", " Nombre ", " Min ", " PTS ", " TL ", " "," "," T2 ", " "," "," T3 "," "," ", " Rebotes"," "," ", " Faltas ","", " Balones "," ", " Tapones "," ",
            " As ", " Val "};
    private final String[] header2 = {" ", " ", " ", " ", " TLA ", " TLI ", " TL% ", " T2A ", " T2I ", " T2% ", " T3A ", " T3I ", " T3% ", " TOT ", " DEF ", " OF ", " COM ", " REC ",
            " REC ", " PER ", " REC ", " COM ", " ", " "};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOutputMatchesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        partidoviewmodel = new ViewModelProvider(requireActivity()).get(PartidoViewModel.class);
        viewmodel = new ViewModelProvider(requireActivity()).get(StatsOnViewModel.class);

        //locales
        TableRow nombreLocal=new TableRow(requireActivity());
        TextView ts1=new TextView(requireActivity());
        ts1.setTextColor(Color.WHITE);
        ts1.setText(partidoviewmodel.partido.nombreEquipoLocal);
        ts1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ts1.setPadding(0, 25, 0, 25);
        nombreLocal.addView(ts1);
        nombreLocal.setBackgroundColor(Color.DKGRAY);
        binding.table.addView(nombreLocal);

        TableRow rowHeaderLocal = getTableRow2(header);
        rowHeaderLocal.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeaderLocal);

        TableRow rowHeader2Local = getTableRow(header2);
        rowHeader2Local.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeader2Local);

        llenarInforme(partidoviewmodel.jugadoresEquipoLocal,contador);

        //visitantes
        TableRow nombreVisitante=new TableRow(requireActivity());
        TextView ts2=new TextView(requireActivity());
        ts2.setTextColor(Color.WHITE);
        ts2.setText(partidoviewmodel.partido.nombreEquipoVisitante);
        ts2.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ts2.setPadding(0, 25, 0, 25);
        nombreVisitante.addView(ts2);
        nombreVisitante.setBackgroundColor(Color.DKGRAY);
        binding.table.addView(nombreVisitante);

        TableRow rowHeaderVisitante = getTableRow2(header);
        rowHeaderVisitante.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeaderVisitante);

        TableRow rowHeader2Visitante = getTableRow(header2);
        rowHeader2Visitante.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeader2Visitante);

        llenarInforme(partidoviewmodel.jugadoresEquipoVisitante,contador);
    }


    private TableRow getTableRow2(String[] header) {
        TableRow rowHeaderVisitante = new TableRow(requireActivity());
        for (String s : header) {
            TextView tv1 = new TextView(requireActivity());
            tv1.setTextColor(Color.WHITE);
            tv1.setText(s);
            tv1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            rowHeaderVisitante.addView(tv1);
        }
        return rowHeaderVisitante;
    }

    private TableRow getTableRow(String[] header2) {
        TableRow rowHeader2Visitante = new TableRow(requireActivity());
        for (String s : header2) {
            TextView tv1 = setTextToView(s);
            tv1.setTextColor(Color.WHITE);
            tv1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
            rowHeader2Visitante.addView(tv1);
        }
        return rowHeader2Visitante;
    }

    public int calculatePercentage(double obtained, double total) {
        return (int) (obtained * 100 / total);
    }
    private void llenarInforme(List<Jugador> jugadors, int contador) {
        for (Jugador j : jugadors) {
            TableRow row = new TableRow(requireActivity());

            String dorsal = j.dorsal+" " ;
            String nombre = String.valueOf(j.nombre);
            String min = "";
            String puntos = String.valueOf(j.puntos);

            String tla = String.valueOf(j.t1mas);
            int tli = j.t1mas + j.t1menos;
            String tliJugador = String.valueOf(tli);
            String tlporcentaje;
            if (tli == 0) {
                tlporcentaje = "";
            } else {
                int tlporcentajeL = calculatePercentage(j.t1mas, tli);
                tlporcentaje = tlporcentajeL+"%";
            }

            String t2aJugador = String.valueOf(j.t2mas);
            int t2i = j.t2mas + j.t2menos;
            String t2iLocal = String.valueOf(t2i);
            String t2porcentaje;
            if (t2i == 0) {
                t2porcentaje = "";
            } else {
                int t2porcentajej = calculatePercentage(j.t2mas, t2i);
                t2porcentaje = t2porcentajej+"%";
            }


            String t3aJugador = String.valueOf(j.t3mas);
            int t3i = j.t3mas + j.t3menos;
            String t3iJugador = String.valueOf(t3i);
            String t3porcentaje;
            if (t3i == 0) {
                t3porcentaje = "";
            } else {
                int t3porcentajej = calculatePercentage(j.t3mas, t3i);
                t3porcentaje = t3porcentajej+"%";
            }

            if (t2porcentaje.equals("")){
                t2porcentaje = "0%";
            }
            if (tlporcentaje.equals("")){
                tlporcentaje = "0%";
            }
            if (t3porcentaje.equals("")){
                t3porcentaje = "0%";
            }



            String rebotesTotal = String.valueOf(j.rebotes);
            String rebotesDef = String.valueOf(j.rebotesDef);
            String rebotesOf = String.valueOf(j.rebotesOf);

            String faltasCometidas = String.valueOf(j.faltasCometidas);
            String faltasRecibidas = String.valueOf(j.faltasRecibidas);

            String robos = String.valueOf(j.robos);
            String perdidas = String.valueOf(j.perdidas);

            String taponesRecibidos = String.valueOf(j.taponesRecibidos);
            String tapones = String.valueOf(j.tapones);

            String asistencias = String.valueOf(j.asistencias);

            int valoracionRecuento = j.puntos + j.rebotes + j.faltasRecibidas + j.robos + j.tapones + j.asistencias - j.t1menos - j.t2menos - j.t3menos -
                    j.faltasCometidas - j.perdidas - j.taponesRecibidos;
            String valoracion = String.valueOf(valoracionRecuento);

            TextView tv1 = setTextToView(dorsal);
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            TextView tv2 = setTextToView(nombre);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            TextView tv3 = setTextToView(min);
            TextView tv4 = setTextToView(puntos);
            TextView tv5 = setTextToView(tla);
            TextView tv6 = setTextToView(tliJugador);
            TextView tv7 = setTextToView(tlporcentaje);
            TextView tv8 = setTextToView(t2aJugador);
            TextView tv9 = setTextToView(t2iLocal);
            TextView tv10 = setTextToView(t2porcentaje);
            TextView tv11 = setTextToView(t3aJugador);
            TextView tv12 = setTextToView(t3iJugador);
            TextView tv13 = setTextToView(t3porcentaje);
            TextView tv14 = setTextToView(rebotesTotal);
            TextView tv15 = setTextToView(rebotesDef);
            TextView tv16 = setTextToView(rebotesOf);
            TextView tv17 = setTextToView(faltasCometidas);
            TextView tv18 = setTextToView(faltasRecibidas);
            TextView tv19 = setTextToView(robos);
            TextView tv20 = setTextToView(perdidas);
            TextView tv21 = setTextToView(taponesRecibidos);
            TextView tv22 = setTextToView(tapones);
            TextView tv26 = setTextToView(asistencias);
            TextView tv27 = setTextToView(valoracion);

            addViewsToRow(row, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv26, tv27);
            if (contador %2==0){
                row.setBackgroundResource(R.drawable.borde_graella);
            }else {row.setBackgroundResource(R.drawable.borde_graella_2);}
            binding.table.addView(row);
            contador++;
        }
    }

    private TextView setTextToView(String dorsalLocal) {
        TextView tv1 = new TextView(requireActivity());
        tv1.setText(dorsalLocal);
        tv1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        return tv1;
    }

    private void addViewsToRow(TableRow row, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6, TextView tv7, TextView tv8, TextView tv9, TextView tv10, TextView tv11, TextView tv12, TextView tv13, TextView tv14, TextView tv15, TextView tv16, TextView tv17, TextView tv18, TextView tv19, TextView tv20, TextView tv21, TextView tv22, TextView tv26, TextView tv27) {
        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        row.addView(tv4);
        row.addView(tv5);
        row.addView(tv6);
        row.addView(tv7);
        row.addView(tv8);
        row.addView(tv9);
        row.addView(tv10);
        row.addView(tv11);
        row.addView(tv12);
        row.addView(tv13);
        row.addView(tv14);
        row.addView(tv15);
        row.addView(tv16);
        row.addView(tv17);
        row.addView(tv18);
        row.addView(tv19);
        row.addView(tv20);
        row.addView(tv21);
        row.addView(tv22);
        row.addView(tv26);
        row.addView(tv27);
    }
}