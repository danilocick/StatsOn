package com.example.mp07_statson;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.databinding.FragmentPartidoBinding;

import java.util.List;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class PartidoFragment extends BaseFragment {

    private FragmentPartidoBinding binding;
    private int contador = 0;

    private String[] header = {"Dorsal ", " Nombre ", " Min ", " PTS ", " TL ", " "," "," T2 ", " "," "," T3 "," "," ", " Rebotes"," "," ", " Faltas ","", " Balones "," ", " Tapones "," ",
            " As ", " Val "};
    private String[] header2 = {" ", " ", " ", " ", " TLA ", " TLI ", " TL% ", " T2A ", " T2I ", " T2% ", " T3A ", " T3I ", " T3% ", " TOT ", " DEF ", " OF ", " COM ", " REC ",
            " REC ", " PER ", " REC ", " COM ", " ", " "};

    private String[] headerCuartos = {" Equipo ", " Periodo 1 ", " Periodo 2 ", " Periodo 3 ", " Periodo 4 "};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentPartidoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        binding.botoncompartir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
                compartir.setType("text/plain");
                String mensaje = viewmodel.partido.archivoCSV;
                compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, viewmodel.partido.nombreEquipoLocal+" VS "+viewmodel.partido.nombreEquipoVisitante);
                compartir.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                startActivity(Intent.createChooser(compartir, "Compartir vía"));
            }
        });

        //locales
        TableRow nombreLocal=new TableRow(requireActivity());
        TextView ts1=new TextView(requireActivity());
        ts1.setTextColor(Color.WHITE);
        ts1.setText(viewmodel.partido.nombreEquipoLocal);
        ts1.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ts1.setPadding(0, 25, 0, 25);
        nombreLocal.addView(ts1);
        int drawable = ContextCompat.getColor(getContext(),R.color.greydark);
        nombreLocal.setBackgroundColor(drawable);
        binding.table.addView(nombreLocal);

        TableRow rowHeaderLocal = getTableRow2(header);
        rowHeaderLocal.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeaderLocal);

        TableRow rowHeader2Local = getTableRow(header2);
        rowHeader2Local.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeader2Local);

        contador = llenarInforme(partidoviewmodel.jugadoresEquipoLocal,contador);

        llenarEquipoLocal(viewmodel.partido);

        //visitante
        TableRow nombreVisitante=new TableRow(requireActivity());
        TextView ts2=new TextView(requireActivity());
        ts2.setTextColor(Color.WHITE);
        ts2.setText(viewmodel.partido.nombreEquipoVisitante);
        ts2.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        ts2.setPadding(0, 25, 0, 25);
        nombreVisitante.addView(ts2);
        int drawable2 = ContextCompat.getColor(getContext(),R.color.greydark);
        nombreVisitante.setBackgroundColor(drawable2);
        binding.table.addView(nombreVisitante);

        TableRow rowHeaderVisitante = getTableRow2(header);
        rowHeaderVisitante.setBackgroundColor(Color.BLACK);

        TableRow rowHeader2Visitante = getTableRow(header2);
        rowHeader2Visitante.setBackgroundColor(Color.BLACK);

        binding.table.addView(rowHeaderVisitante);
        binding.table.addView(rowHeader2Visitante);

        contador = llenarInforme(partidoviewmodel.jugadoresEquipoVisitante, contador);
        llenarEquipoVisitante(viewmodel.partido);

        TableRow rowHeaderCuartos = getTableRow2(headerCuartos);
        rowHeaderCuartos.setBackgroundColor(Color.BLACK);
        binding.table.addView(rowHeaderCuartos);

        llenarCuartos(viewmodel.partido);
    }

    private void llenarCuartos(Partido p) {
        TableRow rowEquipoLocal = new TableRow(requireActivity());

        String nombreL =  p.nombreEquipoLocal;
        String periodo1L = String.valueOf(p.puntosLocalPrimerCuarto);
        String periodo2L = String.valueOf(p.puntosLocalSegundoCuarto);
        String periodo3L = String.valueOf(p.puntosLocalTercerCuarto);
        String periodo4L = String.valueOf(p.puntosLocalQuartoCuarto);

        TextView tv1 = setTextToView(nombreL);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv2 = setTextToView(periodo1L);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv3 = setTextToView(periodo2L);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv4 = setTextToView(periodo3L);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv5 = setTextToView(periodo4L);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);

        rowEquipoLocal.addView(tv1);
        rowEquipoLocal.addView(tv2);
        rowEquipoLocal.addView(tv3);
        rowEquipoLocal.addView(tv4);
        rowEquipoLocal.addView(tv5);

        rowEquipoLocal.setBackgroundResource(R.drawable.borde_graella);
        binding.table.addView(rowEquipoLocal);

        TableRow rowEquipoVisitante = new TableRow(requireActivity());

        String nombreV =  p.nombreEquipoVisitante;
        String periodo1V = String.valueOf(p.puntosVisitantePrimerCuarto);
        String periodo2V = String.valueOf(p.puntosVisitanteSegundoCuarto);
        String periodo3V = String.valueOf(p.puntosVisitanteTercerCuarto);
        String periodo4V = String.valueOf(p.puntosVisitanteQuartoCuarto);

        TextView tv6 = setTextToView(nombreV);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv7 = setTextToView(periodo1V);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv8 = setTextToView(periodo2V);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv9 = setTextToView(periodo3V);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv10 = setTextToView(periodo4V);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        tv6.setTextColor(Color.BLACK);
        tv7.setTextColor(Color.BLACK);
        tv8.setTextColor(Color.BLACK);
        tv9.setTextColor(Color.BLACK);
        tv10.setTextColor(Color.BLACK);

        rowEquipoVisitante.addView(tv6);
        rowEquipoVisitante.addView(tv7);
        rowEquipoVisitante.addView(tv8);
        rowEquipoVisitante.addView(tv9);
        rowEquipoVisitante.addView(tv10);

        rowEquipoVisitante.setBackgroundResource(R.drawable.borde_graella_2);
        binding.table.addView(rowEquipoVisitante);
    }

    private void llenarEquipoLocal(Partido p) {
        TableRow rowEquipo = new TableRow(requireActivity());

        String dorsal = " " ;
        String nombre = p.nombreEquipoLocal;
        String min = "";
        String puntos = String.valueOf(p.puntosLocal);

        String tla = String.valueOf(p.t1masLocal);
        int tli = p.t1masLocal + p.t1menosLocal;
        String tliEquipo = String.valueOf(tli);
        String tlporcentaje;
        if (tli == 0) {
            tlporcentaje = "";
        } else {
            Long tlporcentajeL = (long) (p.t1masLocal / tli);
            tlporcentaje = String.valueOf(tlporcentajeL);
        }

        String t2aJugador = String.valueOf(p.t2masLocal);
        int t2i = p.t2masLocal + p.t2menosLocal;
        String t2iLocal = String.valueOf(t2i);
        String t2porcentajeLocal;
        if (t2i == 0) {
            t2porcentajeLocal = "";
        } else {
            Long t2porcentaje = (long) (p.t2masLocal / t2i);
            t2porcentajeLocal = String.valueOf(t2porcentaje);
        }


        String t3aJugador = String.valueOf(p.t3masLocal);
        int t3i = p.t3masLocal + p.t3menosLocal;
        String t3iJugador = String.valueOf(t3i);
        String t3porcentaje;
        if (t3i == 0) {
            t3porcentaje = "";
        } else {
            Long t3porcentajej = (long) (p.t3masLocal / t3i);
            t3porcentaje = String.valueOf(t3porcentajej);
        }


        String rebotesTotal = String.valueOf(p.rebotesLocal);
        String rebotesDef = String.valueOf(p.rebotesDefLocal);
        String rebotesOf = String.valueOf(p.rebotesOfLocal);

        String faltasCometidas = String.valueOf(p.faltasCometidasLocal);
        String faltasRecibidas = String.valueOf(p.faltasRecibidasLocal);

        String robos = String.valueOf(p.robosLocal);
        String perdidas = String.valueOf(p.perdidasLocal);

        String taponesRecibidos = String.valueOf(p.taponesRecibidosLocal);
        String tapones = String.valueOf(p.taponesLocal);

        String asistencias = String.valueOf(p.asistenciasLocal);

        int valoracionRecuento = p.puntosLocal + p.rebotesLocal + p.faltasRecibidasLocal + p.robosLocal + p.taponesLocal + p.asistenciasLocal - p.t1menosLocal -
                p.t2menosLocal - p.t3menosLocal - p.faltasCometidasLocal - p.perdidasLocal - p.taponesRecibidosLocal;
        String valoracion = String.valueOf(valoracionRecuento);

        TextView tv1 = setTextToView(dorsal);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv2 = setTextToView(nombre);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        TextView tv3 = setTextToView(min);
        TextView tv4 = setTextToView(puntos);
        TextView tv5 = setTextToView(tla);
        TextView tv6 = setTextToView(tliEquipo);
        TextView tv7 = setTextToView(tlporcentaje);
        TextView tv8 = setTextToView(t2aJugador);
        TextView tv9 = setTextToView(t2iLocal);
        TextView tv10 = setTextToView(t2porcentajeLocal);
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
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);
        tv7.setTextColor(Color.BLACK);
        tv8.setTextColor(Color.BLACK);
        tv9.setTextColor(Color.BLACK);
        tv10.setTextColor(Color.BLACK);
        tv11.setTextColor(Color.BLACK);
        tv12.setTextColor(Color.BLACK);
        tv13.setTextColor(Color.BLACK);
        tv14.setTextColor(Color.BLACK);
        tv15.setTextColor(Color.BLACK);
        tv16.setTextColor(Color.BLACK);
        tv17.setTextColor(Color.BLACK);
        tv18.setTextColor(Color.BLACK);
        tv19.setTextColor(Color.BLACK);
        tv20.setTextColor(Color.BLACK);
        tv21.setTextColor(Color.BLACK);
        tv22.setTextColor(Color.BLACK);
        tv26.setTextColor(Color.BLACK);
        tv27.setTextColor(Color.BLACK);

        addViewsToRow(rowEquipo, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv26, tv27);
        if (contador % 2 == 0){
            rowEquipo.setBackgroundResource(R.drawable.borde_graella);
        } else rowEquipo.setBackgroundResource(R.drawable.borde_graella_2);
        binding.table.addView(rowEquipo);

    }

    private void llenarEquipoVisitante(Partido p) {
        TableRow rowEquipo = new TableRow(requireActivity());

        String dorsal = " " ;
        String nombre = p.nombreEquipoVisitante;
        String min = "";
        String puntos = String.valueOf(p.puntosVisitante);

        String tla = String.valueOf(p.t1masVisitante);
        int tli = p.t1masVisitante + p.t1menosVisitante;
        String tliEquipo = String.valueOf(tli);
        String tlporcentaje;
        if (tli == 0) {
            tlporcentaje = "";
        } else {
            Long tlporcentajeL = (long) (p.t1masVisitante / tli);
            tlporcentaje = String.valueOf(tlporcentajeL);
        }

        String t2aJugador = String.valueOf(p.t2masVisitante);
        int t2i = p.t2masVisitante + p.t2menosVisitante;
        String t2iLocal = String.valueOf(t2i);
        String t2porcentajeLocal;
        if (t2i == 0) {
            t2porcentajeLocal = "";
        } else {
            Long t2porcentaje = (long) (p.t2masVisitante / t2i);
            t2porcentajeLocal = String.valueOf(t2porcentaje);
        }


        String t3aJugador = String.valueOf(p.t3masVisitante);
        int t3i = p.t3masVisitante + p.t3menosVisitante;
        String t3iJugador = String.valueOf(t3i);
        String t3porcentaje;
        if (t3i == 0) {
            t3porcentaje = "";
        } else {
            Long t3porcentajej = (long) (p.t3masVisitante / t3i);
            t3porcentaje = String.valueOf(t3porcentajej);
        }


        String rebotesTotal = String.valueOf(p.rebotesVisitante);
        String rebotesDef = String.valueOf(p.rebotesDefVisitante);
        String rebotesOf = String.valueOf(p.rebotesOfVisitante);

        String faltasCometidas = String.valueOf(p.faltasCometidasVisitante);
        String faltasRecibidas = String.valueOf(p.faltasRecibidasVisitante);

        String robos = String.valueOf(p.robosVisitante);
        String perdidas = String.valueOf(p.perdidasVisitante);

        String taponesRecibidos = String.valueOf(p.taponesRecibidosVisitante);
        String tapones = String.valueOf(p.taponesVisitante);

        String asistencias = String.valueOf(p.asistenciasVisitante);

        int valoracionRecuento = p.puntosVisitante + p.rebotesVisitante + p.faltasRecibidasVisitante + p.robosVisitante + p.taponesVisitante + p.asistenciasVisitante -
                p.t1menosVisitante - p.t2menosVisitante - p.t3menosVisitante - p.faltasCometidasVisitante - p.perdidasVisitante - p.taponesRecibidosVisitante;
        String valoracion = String.valueOf(valoracionRecuento);

        TextView tv1 = setTextToView(dorsal);
        tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TextView tv2 = setTextToView(nombre);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        TextView tv3 = setTextToView(min);
        TextView tv4 = setTextToView(puntos);
        TextView tv5 = setTextToView(tla);
        TextView tv6 = setTextToView(tliEquipo);
        TextView tv7 = setTextToView(tlporcentaje);
        TextView tv8 = setTextToView(t2aJugador);
        TextView tv9 = setTextToView(t2iLocal);
        TextView tv10 = setTextToView(t2porcentajeLocal);
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
        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);
        tv7.setTextColor(Color.BLACK);
        tv8.setTextColor(Color.BLACK);
        tv9.setTextColor(Color.BLACK);
        tv10.setTextColor(Color.BLACK);
        tv11.setTextColor(Color.BLACK);
        tv12.setTextColor(Color.BLACK);
        tv13.setTextColor(Color.BLACK);
        tv14.setTextColor(Color.BLACK);
        tv15.setTextColor(Color.BLACK);
        tv16.setTextColor(Color.BLACK);
        tv17.setTextColor(Color.BLACK);
        tv18.setTextColor(Color.BLACK);
        tv19.setTextColor(Color.BLACK);
        tv20.setTextColor(Color.BLACK);
        tv21.setTextColor(Color.BLACK);
        tv22.setTextColor(Color.BLACK);
        tv26.setTextColor(Color.BLACK);
        tv27.setTextColor(Color.BLACK);

        addViewsToRow(rowEquipo, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv26, tv27);
        if (contador % 2 == 0){
            rowEquipo.setBackgroundResource(R.drawable.borde_graella);
        } else rowEquipo.setBackgroundResource(R.drawable.borde_graella_2);
        binding.table.addView(rowEquipo);

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

    private int llenarInforme(List<Jugador> jugadors, int contador) {
        for (Jugador j : jugadors) {

            TableRow row = new TableRow(requireActivity());

            String dorsal = String.valueOf(j.dorsal)+" " ;
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
                Long tlporcentajeL = (long) (j.t1mas / tli);
                tlporcentaje = String.valueOf(tlporcentajeL);
            }

            String t2aJugador = String.valueOf(j.t2mas);
            int t2i = j.t2mas + j.t2menos;
            String t2iLocal = String.valueOf(t2i);
            String t2porcentajeLocal;
            if (t2i == 0) {
                t2porcentajeLocal = "";
            } else {
                Long t2porcentaje = (long) (j.t2mas / t2i);
                t2porcentajeLocal = String.valueOf(t2porcentaje);
            }


            String t3aJugador = String.valueOf(j.t3mas);
            int t3i = j.t3mas + j.t3menos;
            String t3iJugador = String.valueOf(t3i);
            String t3porcentaje;
            if (t3i == 0) {
                t3porcentaje = "";
            } else {
                Long t3porcentajej = (long) (j.t3mas / t3i);
                t3porcentaje = String.valueOf(t3porcentajej);
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
            tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv1.setTextColor(Color.BLACK);
            TextView tv2 = setTextToView(nombre);
            tv2.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            TextView tv3 = setTextToView(min);
            TextView tv4 = setTextToView(puntos);
            TextView tv5 = setTextToView(tla);
            TextView tv6 = setTextToView(tliJugador);
            TextView tv7 = setTextToView(tlporcentaje);
            TextView tv8 = setTextToView(t2aJugador);
            TextView tv9 = setTextToView(t2iLocal);
            TextView tv10 = setTextToView(t2porcentajeLocal);
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
            tv2.setTextColor(Color.BLACK);
            tv3.setTextColor(Color.BLACK);
            tv4.setTextColor(Color.BLACK);
            tv5.setTextColor(Color.BLACK);
            tv6.setTextColor(Color.BLACK);
            tv7.setTextColor(Color.BLACK);
            tv8.setTextColor(Color.BLACK);
            tv9.setTextColor(Color.BLACK);
            tv10.setTextColor(Color.BLACK);
            tv11.setTextColor(Color.BLACK);
            tv12.setTextColor(Color.BLACK);
            tv13.setTextColor(Color.BLACK);
            tv14.setTextColor(Color.BLACK);
            tv15.setTextColor(Color.BLACK);
            tv16.setTextColor(Color.BLACK);
            tv17.setTextColor(Color.BLACK);
            tv18.setTextColor(Color.BLACK);
            tv19.setTextColor(Color.BLACK);
            tv20.setTextColor(Color.BLACK);
            tv21.setTextColor(Color.BLACK);
            tv22.setTextColor(Color.BLACK);
            tv26.setTextColor(Color.BLACK);
            tv27.setTextColor(Color.BLACK);


            addViewsToRow(row, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21, tv22, tv26, tv27);

            if (contador % 2 == 0){
                row.setBackgroundResource(R.drawable.borde_graella);
            } else row.setBackgroundResource(R.drawable.borde_graella_2);

            binding.table.addView(row);
            contador++;
        }
        return contador;
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