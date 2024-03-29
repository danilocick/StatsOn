package com.example.mp07_statson;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.Accion;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.Model.Ppp;
import com.example.mp07_statson.databinding.FragmentGameBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GameFragment extends BaseFragment {

    private FragmentGameBinding binding;
    private List<ImageButton> botonesAcciones;
    private List<LinearLayout> botonesJugadoresLocales;
    private List<LinearLayout> botonesJugadoresVisitantes;

    private List<TextView> dorsalesJugadoresLocales;
    private List<TextView> dorsalesJugadoresVisitantes;

    private List<TextView> puntosJugadoresLocales;
    private List<TextView> puntosJugadoresVisitantes;
    private List<TextView> faltasJugadoresLocales;
    private List<TextView> faltasJugadoresVisitantes;
    private List<TextView> nombresJugadoresLocales;
    private List<TextView> nombresJugadoresVisitantes;

    /*https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        botonesJugadoresLocales = Arrays.asList(binding.jugA1, binding.jugA2, binding.jugA3, binding.jugA4, binding.jugA5);
        dorsalesJugadoresLocales = Arrays.asList(binding.dorsalA1, binding.dorsalA2, binding.dorsalA3, binding.dorsalA4, binding.dorsalA5);
        puntosJugadoresLocales = Arrays.asList(binding.puntosA1, binding.puntosA2, binding.puntosA3, binding.puntosA4, binding.puntosA5);
        faltasJugadoresLocales = Arrays.asList(binding.faltasA1, binding.faltasA2, binding.faltasA3, binding.faltasA4, binding.faltasA5);
        nombresJugadoresLocales = Arrays.asList(binding.nombreA1,binding.nombreA2,binding.nombreA3,binding.nombreA4,binding.nombreA5);

        botonesJugadoresVisitantes = Arrays.asList(binding.jugB1, binding.jugB2, binding.jugB3, binding.jugB4, binding.jugB5);
        dorsalesJugadoresVisitantes = Arrays.asList(binding.dorsalB1, binding.dorsalB2, binding.dorsalB3, binding.dorsalB4, binding.dorsalB5);
        puntosJugadoresVisitantes = Arrays.asList(binding.puntosB1, binding.puntosB2, binding.puntosB3, binding.puntosB4, binding.puntosB5);
        faltasJugadoresVisitantes = Arrays.asList(binding.faltasB1, binding.faltasB2, binding.faltasB3, binding.faltasB4, binding.faltasB5);
        nombresJugadoresVisitantes = Arrays.asList(binding.nombreB1,binding.nombreB2,binding.nombreB3,binding.nombreB4,binding.nombreB5);

        botonesAcciones = Arrays.asList(binding.imagenThreePointMore, binding.imagenThreePointLess, binding.imagenTwoPointMore, binding.imagenTwoPointLess, binding.imagenFreeThrowMore,
                binding.imagenFreeThrowLess, binding.imagenAsistencia, binding.imagenTaponCometido, binding.imagenTaponRecibido, binding.imagenRobo, binding.imagenPerdida,
                binding.imagenFaltaRecibida, binding.imagenFaltaCometida, binding.imagenReboteOfe, binding.imagenReboteDef);

        partidoviewmodel.partido = new Partido();
        partidoviewmodel.jugadoresEquipoLocalGeneral = new ArrayList<>();
        partidoviewmodel.jugadoresEquipoVisitanteGeneral = new ArrayList<>();
        partidoviewmodel.acciones = new LinkedList<>();
        iniciarPartido();


        binding.botonSP.setOnClickListener(view17 -> {
            if (partidoviewmodel.mTimerRunning) {
                binding.botonSP.setImageResource(R.drawable.boton_pause);
                binding.chronometer.setTextColor(Color.RED);
                pauseTimer();
            } else {
                startTimer();
                binding.botonSP.setImageResource(R.drawable.boton_play);
                binding.chronometer.setTextColor(Color.GREEN);
            }
        });
        updateCountDownText();
        cargarPantalla();

        binding.botonVistaPrevia.setOnClickListener(view15 -> nav.navigate(R.id.action_gameFragment_to_outputMatchesFragment));

        binding.siguienteCuarto.setOnClickListener(v->{
            if (partidoviewmodel.cuarto < viewmodel.periodos){
                partidoviewmodel.cuarto++;
                pasarcuarto();
            }
        });
        binding.cuartoAnterior.setOnClickListener(v->{
            if (partidoviewmodel.cuarto >1){
                partidoviewmodel.cuarto--;
                pasarcuarto();
            }
        });

        partidoviewmodel.repintarEquipoLocal.observe(getViewLifecycleOwner(), aBoolean -> {
            printarJugadoresEnNombreLocal();
            printarJugadoresLocal();
        });
        partidoviewmodel.repintarEquipoVisitante.observe(getViewLifecycleOwner(), aBoolean -> {
            printarJugadoresEnNombreVisitante();
            printarJugadoresVisitante();
        });

        partidoviewmodel.anyadir_segundos.observe(getViewLifecycleOwner(), aBoolean -> {
            for (Jugador j : partidoviewmodel.jugadoresEquipoLocal){
                if (j.starter){
                    if(j.segundos_jugados+2 == 60){
                        j.segundos_jugados = 0;
                        j.minutos_jugados++;
                    }else {
                        j.segundos_jugados+=2;
                    }
                }
            }
            for (Jugador j : partidoviewmodel.jugadoresEquipoVisitante){
                if (j.starter){
                    if(j.segundos_jugados+2 == 60){
                        j.segundos_jugados = 0;
                        j.minutos_jugados++;
                    }else {
                        j.segundos_jugados+=2;
                    }
                }
            }
        });

        int i = 0;
        for (LinearLayout jugadorLocal : botonesJugadoresLocales) {
            final int ii = i;

            jugadorLocal.setOnLongClickListener(v1->{
                partidoviewmodel.seleccionEquipo = true;
                partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).starter = false;
                nav.navigate(R.id.action_gameFragment_to_cambioFragment);
                return false;
            });

            jugadorLocal.setOnClickListener(v -> {
                seleccionaJugador(jugadorLocal);

                binding.imagenThreePointMore.setOnClickListener(v1 -> {
                    partidoviewmodel.partido.puntosLocal += 3;
                    partidoviewmodel.partido.t3masLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).puntos += 3;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t3mas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos += 3;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t3mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto+=3; break;
                        case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto+=3; break;
                        case 3: partidoviewmodel.partido.puntosLocalTercerCuarto+=3; break;
                        case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto+=3; break;
                    }

                    binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));
                    puntosJugadoresLocales.get(ii).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos));

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"t3mas");
                });

                binding.imagenThreePointLess.setOnClickListener(v1 -> {
                    partidoviewmodel.partido.t3menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t3menos += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t3menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"t3menos");
                });

                binding.imagenTwoPointMore.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.puntosLocal += 2;
                    partidoviewmodel.partido.t2masLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).puntos += 2;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t2mas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos += 2;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t2mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto+=2; break;
                        case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto+=2; break;
                        case 3: partidoviewmodel.partido.puntosLocalTercerCuarto+=2; break;
                        case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto+=2; break;
                    }

                    puntosJugadoresLocales.get(ii).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos));

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"t2mas");
                });

                binding.imagenTwoPointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t2menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t2menos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t2menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"t2menos");
                });

                binding.imagenFreeThrowMore.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.puntosLocal += 1;
                    partidoviewmodel.partido.t1masLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).puntos += 1;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t1mas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t1mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto+=1; break;
                        case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto+=1; break;
                        case 3: partidoviewmodel.partido.puntosLocalTercerCuarto+=1; break;
                        case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto+=1; break;
                    }

                    puntosJugadoresLocales.get(ii).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).puntos));

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"tirolibremas");
                });

                binding.imagenFreeThrowLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t1menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t1menos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t1menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"tirolibremenos");
                });

                binding.imagenAsistencia.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.asistenciasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).asistencias += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).asistencias += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"asistencia");
                });

                binding.imagenTaponCometido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).tapones += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).tapones += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"taponcometido");
                });

                binding.imagenTaponRecibido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesRecibidosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).taponesRecibidos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).taponesRecibidos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"taponrecibido");
                });

                binding.imagenRobo.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.robosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).robos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).robos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"robo");
                });

                binding.imagenPerdida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.perdidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).perdidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).perdidas += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"perdida");
                });

                binding.imagenFaltaRecibida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasRecibidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).faltasRecibidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasRecibidas += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"faltarecibida");
                });

                binding.imagenFaltaCometida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasCometidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).faltasCometidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasCometidas += 1;

                    binding.faltasEquipoLocal.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasLocal));
                    faltasJugadoresLocales.get(ii).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasCometidas));

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"faltacometida");
                });

                binding.imagenReboteOfe.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesOfLocal += 1;
                    partidoviewmodel.partido.rebotesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotesOf += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotesOf += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"reboteofensivo");
                });

                binding.imagenReboteDef.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesDefLocal += 1;
                    partidoviewmodel.partido.rebotesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotesDef += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotesDef += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)), true,"rebotedefensivo");
                });
            });
            i++;
        }
        int j = 0;
        for (LinearLayout jugadorvisitante : botonesJugadoresVisitantes) {
            final int jj = j;

            jugadorvisitante.setOnLongClickListener(v1->{
                partidoviewmodel.seleccionEquipo = false;
                partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).starter = false;
                nav.navigate(R.id.action_gameFragment_to_cambioFragment);
                return false;
            });


            jugadorvisitante.setOnClickListener(v1 -> {
                seleccionaJugador(jugadorvisitante);

                binding.imagenThreePointMore.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.puntosVisitante += 3;
                    partidoviewmodel.partido.t3masVisitante += 1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).puntos += 3;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t3mas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos += 3;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t3mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto+=3; break;
                        case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto+=3; break;
                        case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto +=3; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=3; break;
                    }

                    binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));
                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"t3mas");
                });

                binding.imagenThreePointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t3menosVisitante += 1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t3menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t3menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"t3menos");
                });

                binding.imagenTwoPointMore.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.puntosVisitante+=2;
                    partidoviewmodel.partido.t2masVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).puntos += 2;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t2mas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos += 2;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t2mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto+=2; break;
                        case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto+=3; break;
                        case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto +=2; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=2; break;
                    }

                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"t2mas");
                });

                binding.imagenTwoPointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t2menosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t2menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t2menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"t3menos");
                });

                binding.imagenFreeThrowMore.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.puntosVisitante+=1;
                    partidoviewmodel.partido.t1masVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).puntos += 1;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t1mas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos += 1;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t1mas += 1;

                    switch (partidoviewmodel.cuarto){
                        case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto+=1; break;
                        case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto+=1; break;
                        case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto +=1; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=1; break;
                    }

                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"tirolibremas");
                });

                binding.imagenFreeThrowLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t1menosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t1menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t1menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"tirolibremenos");
                });

                binding.imagenAsistencia.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.asistenciasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).asistencias += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).asistencias += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"asistencia");
                });

                binding.imagenTaponCometido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesVisitante +=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).tapones += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).tapones += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"taponcometido");
                });

                binding.imagenTaponRecibido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesRecibidosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).taponesRecibidos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).taponesRecibidos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"taponrecibido");
                });

                binding.imagenRobo.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.robosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).robos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).robos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"robo");
                });

                binding.imagenPerdida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.perdidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).perdidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).perdidas += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"perdida");
                });

                binding.imagenFaltaRecibida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasRecibidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).faltasRecibidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasRecibidas += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"faltarecibida");
                });

                binding.imagenFaltaCometida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasCometidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas += 1;

                    binding.faltasEquipoVisitante.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasVisitante));
                    faltasJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"faltacometida");
                });

                binding.imagenReboteOfe.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesVisitante+=1;
                    partidoviewmodel.partido.rebotesOfVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotesOf += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotesOf += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"reboteofensivo");
                });

                binding.imagenReboteDef.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesVisitante+=1;
                    partidoviewmodel.partido.rebotesDefVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotesDef += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotesDef += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                    anyadirAccion(partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)), false,"rebotedefensivo");
                });
            });
            j++;
        }

        binding.imagenDeshacer.setOnClickListener(v -> {
            restarAccion();
        });

        binding.botonAcabarPartido.setOnClickListener(view16 -> {
            try {
                File f = new GenerarCSV().generarCSV(partidoviewmodel.partido, partidoviewmodel.jugadoresEquipoLocal, partidoviewmodel.jugadoresEquipoVisitante);

                stor.getReference("partidos/"+ f.getName())
                        .putStream(new FileInputStream(f))
                        .continueWithTask(task -> Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl())
                        .addOnSuccessListener(url -> {
                            partidoviewmodel.partido.archivoCSV = url.toString();
                            partidoviewmodel.partido.fecha = new Date();
                            subirPartidoFirebase(partidoviewmodel.partido);
                            nav.navigate(R.id.action_gameFragment_to_menuFragment);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void anyadirAccion(Jugador jugador, boolean equipo, String acc) {
        Accion accionNueva = new Accion(equipo, acc, jugador);

        if(partidoviewmodel.acciones.size()<3){
            partidoviewmodel.acciones.add(accionNueva);
        }else {
            Accion a1 = partidoviewmodel.acciones.get(1);
            Accion a2 = partidoviewmodel.acciones.get(2);
            partidoviewmodel.acciones.set(0,a1);
            partidoviewmodel.acciones.set(1,a2);
            partidoviewmodel.acciones.set(2,accionNueva);
        }
        Log.d("ABCD", "AÑADIR");
        for (Accion a:partidoviewmodel.acciones) {
            Log.d("ABCD", a.toString());
        }
    }

    private void restarAccion() {
        Accion accionATratar;
        if(partidoviewmodel.acciones.size()==1){
            accionATratar = partidoviewmodel.acciones.get(0);
            DeshacerAccion(accionATratar);
            partidoviewmodel.acciones.remove(0);
        }else if(partidoviewmodel.acciones.size()==2){
            accionATratar = partidoviewmodel.acciones.get(1);
            DeshacerAccion(accionATratar);
            partidoviewmodel.acciones.remove(1);
        }else if(partidoviewmodel.acciones.size()==3){
            accionATratar = partidoviewmodel.acciones.get(2);
            DeshacerAccion(accionATratar);
            partidoviewmodel.acciones.remove(2);
        }else{
            Toast.makeText(requireActivity().getApplicationContext(), "No hay más acciones por deshacer", Toast.LENGTH_LONG).show();
        }

        Log.d("ABCD", "ELiminar");
        for (Accion a:partidoviewmodel.acciones) {
            Log.d("ABCD", a.toString());
        }
    }

    private void DeshacerAccion(Accion accionATratar) {
        if (accionATratar.equipoLocal) {
            switch(accionATratar.jugada) {
                case "t3mas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {

                            partidoviewmodel.partido.puntosLocal -= 3;
                            partidoviewmodel.partido.t3masLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).puntos -= 3;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t3mas -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).puntos -= 3;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).t3mas -= 1;

                            switch (partidoviewmodel.cuarto){
                                case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto-=3; break;
                                case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto-=3; break;
                                case 3: partidoviewmodel.partido.puntosLocalTercerCuarto-=3; break;
                                case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto-=3; break;
                            }

                            binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));
                            puntosJugadoresLocales.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                            break;
                        }
                    }
                    break;
                case "t3menos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t3menosLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t3menos -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).t3menos -= 1;
                            break;
                        }
                    }
                    break;
                case "t2mas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.puntosLocal -= 2;
                            partidoviewmodel.partido.t2masLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).puntos -= 2;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t2mas -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).puntos -= 2;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).t2mas -= 1;

                            switch (partidoviewmodel.cuarto) {
                                case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto -= 2; break;
                                case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto -= 2; break;
                                case 3: partidoviewmodel.partido.puntosLocalTercerCuarto -= 2; break;
                                case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto -= 2; break;
                            }

                            puntosJugadoresLocales.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                            break;
                        }
                    }
                    break;
                case "t2menos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t2menosLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t2menos -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).t2menos -= 1;
                            break;
                        }
                    }
                    break;
                case "tirolibremas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.puntosLocal -= 1;
                            partidoviewmodel.partido.t1masLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).puntos -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t1mas -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).puntos -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).t1mas -= 1;

                            switch (partidoviewmodel.cuarto){
                                case 1: partidoviewmodel.partido.puntosLocalPrimerCuarto-=1; break;
                                case 2: partidoviewmodel.partido.puntosLocalSegundoCuarto-=1; break;
                                case 3: partidoviewmodel.partido.puntosLocalTercerCuarto-=1; break;
                                case 4: partidoviewmodel.partido.puntosLocalQuartoCuarto-=1; break;
                            }

                            puntosJugadoresLocales.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));

                            break;
                        }
                    }
                    break;
                case "tirolibremenos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t1menosLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).t1menos -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).t1menos -= 1;
                            break;
                        }
                    }
                    break;
                case "asistencia":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.asistenciasLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).asistencias -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).asistencias -= 1;
                            break;
                        }
                    }
                    break;
                case "taponcometido":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.taponesLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).tapones -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).tapones -= 1;
                            break;
                        }
                    }
                    break;
                case "taponrecibido":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.taponesRecibidosLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).taponesRecibidos -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).taponesRecibidos -= 1;
                            break;
                        }
                    }
                    break;
                case "robo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.robosLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).robos -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).robos -= 1;
                            break;
                        }
                    }
                    break;
                case "perdida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.perdidasLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).perdidas -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).perdidas -= 1;
                            break;
                        }
                    }
                    break;
                case "faltarecibida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.faltasRecibidasLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).faltasRecibidas -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).faltasRecibidas -= 1;
                            break;
                        }
                    }
                    break;
                case "faltacometida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.faltasCometidasLocal -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).faltasCometidas -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas -= 1;

                            binding.faltasEquipoLocal.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasLocal));
                            faltasJugadoresLocales.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                            break;
                        }
                    }
                    break;
                case "reboteofensivo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.rebotesOfLocal -= 1;
                            partidoviewmodel.partido.rebotesLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).rebotesOf -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).rebotesOf -= 1;
                            break;
                        }
                    }
                    break;
                case "rebotedefensivo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocalGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.rebotesDefLocal -= 1;
                            partidoviewmodel.partido.rebotesLocal -= 1;

                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoLocalGeneral.get(i).rebotesDef -= 1;

                            partidoviewmodel.jugadoresEquipoLocal.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoLocal.get(i).rebotesDef -= 1;
                            break;
                        }
                    }
                    break;
            }

        }else {
            switch(accionATratar.jugada) {
                case "t3mas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {

                            partidoviewmodel.partido.puntosVisitante -= 3;
                            partidoviewmodel.partido.t3masVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).puntos -= 3;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t3mas -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos -= 3;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t3mas -= 1;

                            switch (partidoviewmodel.cuarto){
                                case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto-=3; break;
                                case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto-=3; break;
                                case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto-=3; break;
                                case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto-=3; break;
                            }

                            binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));
                            puntosJugadoresVisitantes.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                            break;
                        }
                    }
                    break;
                case "t3menos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t3menosVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t3menos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t3menos -= 1;
                            break;
                        }
                    }
                    break;
                case "t2mas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.puntosVisitante -= 2;
                            partidoviewmodel.partido.t2masVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).puntos -= 2;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t2mas -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos -= 2;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t2mas -= 1;

                            switch (partidoviewmodel.cuarto) {
                                case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto -= 2; break;
                                case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto -= 2; break;
                                case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto -= 2; break;
                                case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto -= 2; break;
                            }

                            puntosJugadoresVisitantes.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                            break;
                        }
                    }
                    break;
                case "t2menos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t2menosVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t2menos -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t2menos -= 1;
                            break;
                        }
                    }
                    break;
                case "tirolibremas":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.puntosVisitante -= 1;
                            partidoviewmodel.partido.t1masVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).puntos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t1mas -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t1mas -= 1;

                            switch (partidoviewmodel.cuarto){
                                case 1: partidoviewmodel.partido.puntosVisitantePrimerCuarto-=1; break;
                                case 2: partidoviewmodel.partido.puntosVisitanteSegundoCuarto-=1; break;
                                case 3: partidoviewmodel.partido.puntosVisitanteTercerCuarto-=1; break;
                                case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto-=1; break;
                            }
                            puntosJugadoresVisitantes.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                            break;
                        }
                    }
                    break;
                case "tirolibremenos":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.t1menosVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).t1menos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).t1menos -= 1;
                            break;
                        }
                    }
                    break;
                case "asistencia":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.asistenciasVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).asistencias -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).asistencias -= 1;
                            break;
                        }
                    }
                    break;
                case "taponcometido":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.taponesVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).tapones -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).tapones -= 1;
                            break;
                        }
                    }
                    break;
                case "taponrecibido":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.taponesRecibidosVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).taponesRecibidos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).taponesRecibidos -= 1;
                            break;
                        }
                    }
                    break;
                case "robo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.robosVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).robos -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).robos -= 1;
                            break;
                        }
                    }
                    break;
                case "perdida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.perdidasVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).perdidas -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).perdidas -= 1;
                            break;
                        }
                    }
                    break;
                case "faltarecibida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.faltasRecibidasVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).faltasRecibidas -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasRecibidas -= 1;
                            break;
                        }
                    }
                    break;
                case "faltacometida":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.faltasCometidasVisitante -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).faltasCometidas -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas -= 1;

                            binding.faltasEquipoVisitante.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasVisitante));
                            faltasJugadoresVisitantes.get(i).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                            break;
                        }
                    }
                    break;
                case "reboteofensivo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.rebotesOfVisitante -= 1;
                            partidoviewmodel.partido.rebotesVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).rebotesOf -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).rebotesOf -= 1;
                            break;
                        }
                    }
                    break;
                case "rebotedefensivo":
                    for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitanteGeneral.size(); i++) {
                        if (partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).dorsal == accionATratar.jugador.dorsal) {
                            partidoviewmodel.partido.rebotesDefVisitante -= 1;
                            partidoviewmodel.partido.rebotesVisitante -= 1;

                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(i).rebotesDef -= 1;

                            partidoviewmodel.jugadoresEquipoVisitante.get(i).rebotes -= 1;
                            partidoviewmodel.jugadoresEquipoVisitante.get(i).rebotesDef -= 1;
                            break;
                        }
                    }
                    break;
            }
        }
    }

    private void iniciarPartido() {
        for (Jugador j:partidoviewmodel.jugadoresEquipoLocal) {
            Jugador jugador = new Jugador(j.idJugador,j.nombre,j.dorsal,j.imagen,j.puntos,j.rebotes,j.asistencias,j.robos,j.perdidas,j.tapones,
                    j.t1mas,j.t1menos,j.t2mas,j.t2menos,j.t3mas,j.t3menos,j.rebotesDef,j.rebotesOf,j.faltasRecibidas,
                    j.faltasCometidas,j.taponesRecibidos,j.starter);
            partidoviewmodel.jugadoresEquipoLocalGeneral.add(jugador);
        }

        for (Jugador j:partidoviewmodel.jugadoresEquipoVisitante) {
            Jugador jugador = new Jugador(j.idJugador,j.nombre,j.dorsal,j.imagen,j.puntos,j.rebotes,j.asistencias,j.robos,j.perdidas,j.tapones,
                    j.t1mas,j.t1menos,j.t2mas,j.t2menos,j.t3mas,j.t3menos,j.rebotesDef,j.rebotesOf,j.faltasRecibidas,
                    j.faltasCometidas,j.taponesRecibidos,j.starter);
            partidoviewmodel.jugadoresEquipoVisitanteGeneral.add(jugador);
        }

        partidoviewmodel.jugadoresEquipoLocal.forEach(Jugador::reiniciar);
        partidoviewmodel.jugadoresEquipoVisitante.forEach(Jugador::reiniciar);

        partidoviewmodel.partido.nombreEquipoLocal = viewmodel.nombreEquipoLocal;
        partidoviewmodel.partido.imagenEquipoLocal = viewmodel.imagenEquipoLocal;
        partidoviewmodel.partido.nombreEquipoVisitante = viewmodel.nombreEquipoVisitante;
        partidoviewmodel.partido.imagenEquipoVisitante = viewmodel.imagenEquipoVisitante;
        partidoviewmodel.partido.idEquipoLocal = viewmodel.idEquipoLocal;
        partidoviewmodel.partido.idEquipoVisitante = viewmodel.idEquipoVisitante;
    }

    private void subirPartidoFirebase(Partido partido) {
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).add(partido).addOnSuccessListener(documentReference -> {
            String idPartido = documentReference.getId();
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido).update("idPartido", idPartido);

            String timeStamp = new SimpleDateFormat("dd-MM").format(Calendar.getInstance().getTime());

            for (Jugador j: partidoviewmodel.jugadoresEquipoLocal) {
                Ppp ppp = new Ppp(j.puntos, partidoviewmodel.partido.nombreEquipoVisitante+" "+timeStamp);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoLocal)
                        .collection(FirebaseVar.JUGADORES).document(j.idJugador).collection(FirebaseVar.PPP).add(ppp);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido)
                        .collection(FirebaseVar.JUGADORESLOCALES).add(j);
            }
             for (Jugador j: partidoviewmodel.jugadoresEquipoVisitante) {
                Ppp ppp = new Ppp(j.puntos, partidoviewmodel.partido.nombreEquipoLocal+" "+timeStamp);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoVisitante)
                        .collection(FirebaseVar.JUGADORES).document(j.idJugador).collection(FirebaseVar.PPP).add(ppp);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido).
                        collection(FirebaseVar.JUGADORESVISITANTES).add(j);
            }

            for (Jugador jugador: partidoviewmodel.jugadoresEquipoLocalGeneral) {
                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoLocal)
                        .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).update(jugador.toHashMap(jugador));
            }
            for (Jugador jugador: partidoviewmodel.jugadoresEquipoVisitanteGeneral) {
                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoVisitante)
                        .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).update(jugador.toHashMap(jugador));
            }
        });
    }

    private void pasarcuarto() {
        switch (viewmodel.minutos){
            case 5: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_5_IN_MILLIS; break;
            case 6: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_6_IN_MILLIS; break;
            case 10: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_10_IN_MILLIS; break;
        }
        updateCountDownText();

        partidoviewmodel.partido.faltasCometidasLocal = 0;
        partidoviewmodel.partido.faltasCometidasVisitante = 0;
        binding.cuarto.setText(String.valueOf(partidoviewmodel.cuarto));
        binding.faltasEquipoLocal.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasLocal));
        binding.faltasEquipoVisitante.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasVisitante));
    }

    private void cargarPantalla() {
        printarJugadoresLocal();
        printarJugadoresVisitante();

        binding.equipoLocal.setText(partidoviewmodel.partido.nombreEquipoLocal);
        binding.equipovisitante.setText(partidoviewmodel.partido.nombreEquipoVisitante);

        botonesAccionesAdmin(botonesAcciones,false);
        botonesJugadoresAdmin(true);

        switch (viewmodel.minutos){
            case 5: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_5_IN_MILLIS; break;
            case 6: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_6_IN_MILLIS; break;
            case 10: partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_10_IN_MILLIS; break;
        }
    }

    private void desSeleccionarJugador(LinearLayout p, int p2) {
        actualizarResultado(botonesAcciones);
        setBackground(p, p2);
    }

    private void seleccionaJugador(LinearLayout jugador) {
        setBackground(jugador, R.drawable.recyclerv_round_white_red);
        botonesJugadoresAdmin(false);
        botonesAccionesAdmin(botonesAcciones, true);
    }

    private void setBackground(LinearLayout p, int p2) {
        p.setBackgroundResource(p2);
    }

    private void actualizarResultado(List<ImageButton> botonesAcciones) {
        binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));
        binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));

        botonesJugadoresAdmin(true);
        botonesAccionesAdmin(botonesAcciones, false);
    }

    private void botonesJugadoresAdmin(boolean b) {
        for (int i = 0; i <botonesJugadoresLocales.size(); i++) {
            botonesJugadoresLocales.get(i).setEnabled(b);
        }
        for (int i = 0; i <botonesJugadoresVisitantes.size(); i++) {
            botonesJugadoresVisitantes.get(i).setEnabled(b);
        }
    }

    private void botonesAccionesAdmin(List<ImageButton> botonesAcciones, boolean b) {
        for (ImageButton i: botonesAcciones) {
            i.setClickable(b);
        }
    }

    private void printarJugadoresEnNombreLocal() {
        for (TextView s: nombresJugadoresLocales){
            s.setText("Jugador");
        }
        for (TextView s: dorsalesJugadoresLocales){
            s.setText("M");
        }
        for (TextView s: puntosJugadoresLocales){
            s.setText(String.valueOf(0));
        }
        for (TextView s: faltasJugadoresLocales){
            s.setText(String.valueOf(0));
        }
    }
    private void printarJugadoresEnNombreVisitante() {
        for (TextView s: nombresJugadoresVisitantes){
            s.setText("Jugador");
        }
        for (TextView s: dorsalesJugadoresVisitantes){
            s.setText("M");
        }
        for (TextView s: puntosJugadoresVisitantes){
            s.setText(String.valueOf(0));
        }
        for (TextView s: faltasJugadoresVisitantes){
            s.setText(String.valueOf(0));
        }
    }

    private void printarJugadoresLocal() {
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
            if (partidoviewmodel.jugadoresEquipoLocal.get(i).starter){
                if (binding.dorsalA1.getText().equals("M")){
                    binding.nombreA1.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.dorsalA2.getText().equals("M")){
                    binding.nombreA2.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.dorsalA3.getText().equals("M")){
                    binding.nombreA3.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.dorsalA4.getText().equals("M")){
                    binding.nombreA4.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.dorsalA5.getText().equals("M")){
                    binding.nombreA5.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }
            }
        }
    }

    private void printarJugadoresVisitante() {
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitante.size(); i++) {
            if (partidoviewmodel.jugadoresEquipoVisitante.get(i).starter){
                if (binding.dorsalB1.getText().equals("M")){
                    binding.nombreB1.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                }else if (binding.dorsalB2.getText().equals("M")){
                    binding.nombreB2.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                }else if (binding.dorsalB3.getText().equals("M")){
                    binding.nombreB3.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                }else if (binding.dorsalB4.getText().equals("M")){
                    binding.nombreB4.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                }else if (binding.dorsalB5.getText().equals("M")){
                    binding.nombreB5.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).faltasCometidas));
                }
            }
        }
    }

    private void startTimer() {
        partidoviewmodel.mCountDownTimer = new CountDownTimer(partidoviewmodel.mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                partidoviewmodel.mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                partidoviewmodel.mTimerRunning = false;
                binding.chronometer.setVisibility(View.INVISIBLE);
            }
        }.start();
        partidoviewmodel.mTimerRunning = true;
    }
    private void pauseTimer() {
        partidoviewmodel.mCountDownTimer.cancel();
        partidoviewmodel.mTimerRunning = false;
    }
    private void updateCountDownText() {
        int minutes = (int) (partidoviewmodel.mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (partidoviewmodel.mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.chronometer.setText(timeLeftFormatted);
        Log.d("ABCD",minutes+":"+seconds);
        if(seconds%2==0){
            if ((minutes != 10 || seconds != 0) && (minutes != 6 || seconds != 0) && (minutes != 5 || seconds != 0) && (minutes != 0 || seconds != 0)) {
                if(partidoviewmodel.segundo_sumado != seconds){
                    partidoviewmodel.segundo_sumado = seconds;
                    partidoviewmodel.anyadir_segundos.setValue(true);
                }
            }
        }

    }

    int buscarPosicionJugadorLocal(int posicion){
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
            if (String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal).equals(dorsalesJugadoresLocales.get(posicion).getText().toString())){
                return i;
            }
        }
        return -1;
    }

    int buscarPosicionJugadorVisitante(int posicion){
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitante.size(); i++) {
            if (String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal).equals(dorsalesJugadoresVisitantes.get(posicion).getText().toString())){
                return i;
            }
        }
        return -1;
    }
}