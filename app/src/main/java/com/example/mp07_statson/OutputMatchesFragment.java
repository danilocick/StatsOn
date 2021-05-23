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
import com.example.mp07_statson.ViewModel.PartidoViewModel;
import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;

public class OutputMatchesFragment extends DialogFragment {

    private FragmentOutputMatchesBinding binding;
    private PartidoViewModel partidoviewmodel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOutputMatchesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        partidoviewmodel = new ViewModelProvider(requireActivity()).get(PartidoViewModel.class);

        String[] header = {"Dorsal ", " Nombre ", " Min ", " PTS ", " TL ", " "," "," T2 ", " "," "," T3 "," "," ", " Rebotes"," "," ", " Faltas ","", " Balones "," ", " Tapones "," ",
                " Pasos ", " Dobles ", " V.T ", " As ", " Val "};
        String[] header2 = {" ", " ", " ", " ", " TLA ", " TLI ", " TL% ", " T2A ", " T2I ", " T2% ", " T3A ", " T3I ", " T3% ", " TOT ", " DEF ", " OF ", " COM ", " REC ",
                " REC ", " PER ", " REC ", " COM ", " ", " ", " ", " ", " "};
        TableRow rowHeaderLocal=new TableRow(requireActivity());
        for (String s:header){
            TextView tv1=new TextView(requireActivity());
            tv1.setTextColor(Color.WHITE);
            tv1.setText(s);
            rowHeaderLocal.addView(tv1);
        }
        rowHeaderLocal.setBackgroundColor(Color.BLACK);

        TableRow rowHeader2Local=new TableRow(requireActivity());
        for (String s:header2){
            TextView tv1 = setTextToView(s);
            tv1.setTextColor(Color.WHITE);
            rowHeader2Local.addView(tv1);
        }
        rowHeader2Local.setBackgroundColor(Color.BLACK);
        //locales
        binding.table.addView(rowHeaderLocal);
        binding.table.addView(rowHeader2Local);

        for (Jugador j : partidoviewmodel.jugadoresEquipoLocal) {

            TableRow row=new TableRow(requireActivity());
            String dorsalLocal = String.valueOf(j.dorsal);
            String nombreLocal = String.valueOf(j.nombre);
            String minLocal = "";
            String puntosLocal = String.valueOf(j.puntos);

            String tlaLocal = String.valueOf(j.t1mas);
            int tli = j.t1mas+j.t1menos;
            String tliLocal = String.valueOf(tli);
//            Long tlporcentaje = (long) (j.t1mas / tli);
//            String tlporcentajeLocal = String.valueOf(tlporcentaje);
            String tlporcentajeLocal ="";

            String t2aLocal = String.valueOf(j.t2mas);
            int t2i = j.t2mas+j.t2menos;
            String t2iLocal = String.valueOf(t2i);
//            Long tlporcentaje = (long) (j.t1mas / tli);
//            String tlporcentajeLocal = String.valueOf(tlporcentaje);
            String t2porcentajeLocal ="";

            String t3aLocal = String.valueOf(j.t3mas);
            int t3i = j.t3mas+j.t3menos;
            String t3iLocal = String.valueOf(tli);
//            Long t3porcentaje = (long) (j.t1mas / tli);
//            String t3porcentajeLocal = String.valueOf(tlporcentaje);
            String t3porcentajeLocal ="";

            String rebotesTotalLocal = String.valueOf(j.rebotes);
            String rebotesDefLocal = String.valueOf(j.rebotesDef);
            String rebotesOfLocal = String.valueOf(j.rebotesOf);

            String faltasCometidasLocal = String.valueOf(j.faltasCometidas);
            String faltasRecibidasLocal = String.valueOf(j.faltasRecibidas);

            String robosLocal = String.valueOf(j.robos);
            String perdidasLocal = String.valueOf(j.perdidas);

            String taponesRecibidosLocal = String.valueOf(j.taponesRecibidos);
            String taponesLocal = String.valueOf(j.tapones);

            String pasosLocal = "";
            String doblesLocal = "";
            String vTLocal = "";
            String asistenciasLocal = String.valueOf(j.asistencias);

            String valoraciónLocal = "";


            TextView tv1 = setTextToView(dorsalLocal);
            TextView tv2 = setTextToView(nombreLocal);
            TextView tv3 = setTextToView(minLocal);
            TextView tv4 = setTextToView(puntosLocal);
            TextView tv5 = setTextToView(tlaLocal);
            TextView tv6 = setTextToView(tliLocal);
            TextView tv7 = setTextToView(tlporcentajeLocal);
            TextView tv8 = setTextToView(t2aLocal);
            TextView tv9 = setTextToView(t2iLocal);
            TextView tv10 = setTextToView(t2porcentajeLocal);
            TextView tv11 = setTextToView(t3aLocal);
            TextView tv12 = setTextToView(t3iLocal);
            TextView tv13 = setTextToView(t3porcentajeLocal);
            TextView tv14 = setTextToView(rebotesTotalLocal);
            TextView tv15 = setTextToView(rebotesDefLocal);
            TextView tv16 = setTextToView(rebotesOfLocal);
            TextView tv17 = setTextToView(faltasCometidasLocal);
            TextView tv18 = setTextToView(faltasRecibidasLocal);
            TextView tv19 = setTextToView(robosLocal);
            TextView tv20 = setTextToView(perdidasLocal);
            TextView tv21 = setTextToView(taponesRecibidosLocal);
            TextView tv22 = setTextToView(taponesLocal);
            TextView tv23 = setTextToView(pasosLocal);
            TextView tv24 = setTextToView(doblesLocal);
            TextView tv25 = setTextToView(vTLocal);
            TextView tv26 = setTextToView(asistenciasLocal);
            TextView tv27 = setTextToView(valoraciónLocal);

            addViewsToRow(row, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27);

            binding.table.addView(row);
        }

        //visitante
        TableRow rowHeaderVisitante=new TableRow(requireActivity());
        for (String s:header){
            TextView tv1=new TextView(requireActivity());
            tv1.setTextColor(Color.WHITE);
            tv1.setText(s);
            rowHeaderVisitante.addView(tv1);
        }
        rowHeaderVisitante.setBackgroundColor(Color.BLACK);

        TableRow rowHeader2Visitante=new TableRow(requireActivity());
        for (String s:header2){
            TextView tv1 = setTextToView(s);
            tv1.setTextColor(Color.WHITE);
            rowHeader2Visitante.addView(tv1);
        }
        rowHeader2Visitante.setBackgroundColor(Color.BLACK);

        binding.table.addView(rowHeaderVisitante);
        binding.table.addView(rowHeader2Visitante);

        for (Jugador j : partidoviewmodel.jugadoresEquipoVisitante) {

            TableRow row=new TableRow(requireActivity());
            String dorsalLocal = String.valueOf(j.dorsal);
            String nombreLocal = String.valueOf(j.nombre);
            String minLocal = "";
            String puntosLocal = String.valueOf(j.puntos);

            String tlaLocal = String.valueOf(j.t1mas);
            int tli = j.t1mas+j.t1menos;
            String tliLocal = String.valueOf(tli);
//            Long tlporcentaje = (long) (j.t1mas / tli);
//            String tlporcentajeLocal = String.valueOf(tlporcentaje);
            String tlporcentajeLocal ="";

            String t2aLocal = String.valueOf(j.t2mas);
            int t2i = j.t2mas+j.t2menos;
            String t2iLocal = String.valueOf(t2i);
//            Long tlporcentaje = (long) (j.t1mas / tli);
//            String tlporcentajeLocal = String.valueOf(tlporcentaje);
            String t2porcentajeLocal ="";

            String t3aLocal = String.valueOf(j.t3mas);
            int t3i = j.t3mas+j.t3menos;
            String t3iLocal = String.valueOf(tli);
//            Long t3porcentaje = (long) (j.t1mas / tli);
//            String t3porcentajeLocal = String.valueOf(tlporcentaje);
            String t3porcentajeLocal ="";

            String rebotesTotalLocal = String.valueOf(j.rebotes);
            String rebotesDefLocal = String.valueOf(j.rebotesDef);
            String rebotesOfLocal = String.valueOf(j.rebotesOf);

            String faltasCometidasLocal = String.valueOf(j.faltasCometidas);
            String faltasRecibidasLocal = String.valueOf(j.faltasRecibidas);

            String robosLocal = String.valueOf(j.robos);
            String perdidasLocal = String.valueOf(j.perdidas);

            String taponesRecibidosLocal = String.valueOf(j.taponesRecibidos);
            String taponesLocal = String.valueOf(j.tapones);

            String pasosLocal = "";
            String doblesLocal = "";
            String vTLocal = "";
            String asistenciasLocal = String.valueOf(j.asistencias);

            String valoraciónLocal = "";


            TextView tv1 = setTextToView(dorsalLocal);
            TextView tv2 = setTextToView(nombreLocal);
            TextView tv3 = setTextToView(minLocal);
            TextView tv4 = setTextToView(puntosLocal);
            TextView tv5 = setTextToView(tlaLocal);
            TextView tv6 = setTextToView(tliLocal);
            TextView tv7 = setTextToView(tlporcentajeLocal);
            TextView tv8 = setTextToView(t2aLocal);
            TextView tv9 = setTextToView(t2iLocal);
            TextView tv10 = setTextToView(t2porcentajeLocal);
            TextView tv11 = setTextToView(t3aLocal);
            TextView tv12 = setTextToView(t3iLocal);
            TextView tv13 = setTextToView(t3porcentajeLocal);
            TextView tv14 = setTextToView(rebotesTotalLocal);
            TextView tv15 = setTextToView(rebotesDefLocal);
            TextView tv16 = setTextToView(rebotesOfLocal);
            TextView tv17 = setTextToView(faltasCometidasLocal);
            TextView tv18 = setTextToView(faltasRecibidasLocal);
            TextView tv19 = setTextToView(robosLocal);
            TextView tv20 = setTextToView(perdidasLocal);
            TextView tv21 = setTextToView(taponesRecibidosLocal);
            TextView tv22 = setTextToView(taponesLocal);
            TextView tv23 = setTextToView(pasosLocal);
            TextView tv24 = setTextToView(doblesLocal);
            TextView tv25 = setTextToView(vTLocal);
            TextView tv26 = setTextToView(asistenciasLocal);
            TextView tv27 = setTextToView(valoraciónLocal);

            addViewsToRow(row, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv23, tv24, tv25, tv26, tv27);

            binding.table.addView(row);
        }

//        binding.botonComeBack.setOnClickListener(view1 -> dismiss());

    }

    private TextView setTextToView(String dorsalLocal) {
        TextView tv1 = new TextView(requireActivity());
        tv1.setText(dorsalLocal);
        return tv1;
    }

    private void addViewsToRow(TableRow row, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6, TextView tv7, TextView tv8, TextView tv9, TextView tv10, TextView tv11, TextView tv12, TextView tv13, TextView tv14, TextView tv15, TextView tv16, TextView tv17, TextView tv18, TextView tv19, TextView tv20, TextView tv21, TextView tv22, TextView tv23, TextView tv24, TextView tv25, TextView tv26, TextView tv27) {
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
        row.addView(tv23);
        row.addView(tv24);
        row.addView(tv25);
        row.addView(tv26);
        row.addView(tv27);
    }
}