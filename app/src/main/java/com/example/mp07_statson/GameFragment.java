package com.example.mp07_statson;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.databinding.FragmentGameBinding;
import com.google.firebase.firestore.SetOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
        iniciarPartido();

        binding.start.setOnClickListener(view17 -> {
            if (partidoviewmodel.mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
        updateCountDownText();

        cargarPantalla();

        binding.botonVistaPrevia.setOnClickListener(view15 -> nav.navigate(R.id.action_gameFragment_to_outputMatchesFragment));

        binding.siguienteCuarto.setOnClickListener(v->{
            if (partidoviewmodel.cuarto <4){
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

        int i = 0;
        for (LinearLayout jugadorLocal : botonesJugadoresLocales) {
            final int ii = i;

            jugadorLocal.setOnLongClickListener(v1->{
                partidoviewmodel.seleccionEquipo = true;
                partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).starter = false;
                nav.navigate(R.id.action_gameFragment_to_cambioFragment);
                printarJugadoresEnNombreLocal();
                printarJugadoresLocal();
                return false;
            });

            jugadorLocal.setOnClickListener(v -> {
                seleccionaJugador(jugadorLocal, botonesJugadoresLocales, botonesJugadoresVisitantes);


                binding.imagenDeshacer.setOnClickListener(v1 -> {
                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

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
                });

                binding.imagenThreePointLess.setOnClickListener(v1 -> {
                    partidoviewmodel.partido.t3menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t3menos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t3menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
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
                });

                binding.imagenTwoPointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t2menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t2menos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t2menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
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
                });

                binding.imagenFreeThrowLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t1menosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).t1menos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).t1menos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenAsistencia.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.asistenciasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).asistencias += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).asistencias += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenTaponCometido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).tapones += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).tapones += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenTaponRecibido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesRecibidosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).taponesRecibidos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).taponesRecibidos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenRobo.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.robosLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).robos += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).robos += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenPerdida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.perdidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).perdidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).perdidas += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenFaltaRecibida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasRecibidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).faltasRecibidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasRecibidas += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenFaltaCometida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasCometidasLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).faltasCometidas += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasCometidas += 1;

                    binding.faltasEquipoLocal.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasLocal));
                    faltasJugadoresLocales.get(ii).setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).faltasCometidas));

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenReboteOfe.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesOfLocal += 1;
                    partidoviewmodel.partido.rebotesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotesOf += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotesOf += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });

                binding.imagenReboteDef.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesDefLocal += 1;
                    partidoviewmodel.partido.rebotesLocal += 1;

                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocalGeneral.get(buscarPosicionJugadorLocal(ii)).rebotesDef += 1;

                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoLocal.get(buscarPosicionJugadorLocal(ii)).rebotesDef += 1;

                    desSeleccionarJugador(jugadorLocal, R.drawable.recyclerv_round_grey_black);
                });
            });
            i++;
        }
        int j = 0;
        for (LinearLayout jugadorvisitante : botonesJugadoresVisitantes) {
            final int jj = j;

            jugadorvisitante.setOnLongClickListener(v1->{
                partidoviewmodel.seleccionEquipo = false;
                partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorLocal(jj)).starter = false;
                nav.navigate(R.id.action_gameFragment_to_cambioFragment);
                printarJugadoresEnNombreVisitante();
                printarJugadoresVisitante();
                return false;
            });


            jugadorvisitante.setOnClickListener(v1 -> {
                seleccionaJugador(jugadorvisitante,botonesJugadoresLocales,botonesJugadoresVisitantes);

                binding.imagenDeshacer.setOnClickListener(v -> {
                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });


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
                        case 3: partidoviewmodel.partido.puntosVisitenteTercerCuarto+=3; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=3; break;
                    }

                    binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));
                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));


                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenThreePointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t3menosVisitante += 1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t3menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t3menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
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
                        case 3: partidoviewmodel.partido.puntosVisitenteTercerCuarto+=2; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=2; break;
                    }

                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenTwoPointLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t2menosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t2menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t2menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
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
                        case 3: partidoviewmodel.partido.puntosVisitenteTercerCuarto+=1; break;
                        case 4: partidoviewmodel.partido.puntosVisitanteQuartoCuarto+=1; break;
                    }

                    puntosJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).puntos));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenFreeThrowLess.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.t1menosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).t1menos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).t1menos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenAsistencia.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.asistenciasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).asistencias += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).asistencias += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenTaponCometido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesVisitante +=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).tapones += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).tapones += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenTaponRecibido.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.taponesRecibidosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).taponesRecibidos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).taponesRecibidos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenRobo.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.robosVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).robos += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).robos += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenPerdida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.perdidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).perdidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).perdidas += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenFaltaRecibida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasRecibidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).faltasRecibidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasRecibidas += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenFaltaCometida.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.faltasCometidasVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas += 1;

                    binding.faltasEquipoVisitante.setText(String.valueOf(partidoviewmodel.partido.faltasCometidasVisitante));
                    faltasJugadoresVisitantes.get(jj).setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).faltasCometidas));

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenReboteOfe.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesVisitante+=1;
                    partidoviewmodel.partido.rebotesOfVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotesOf += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotesOf += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });

                binding.imagenReboteDef.setOnClickListener(view1 -> {
                    partidoviewmodel.partido.rebotesVisitante+=1;
                    partidoviewmodel.partido.rebotesDefVisitante+=1;

                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitanteGeneral.get(buscarPosicionJugadorVisitante(jj)).rebotesDef += 1;

                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotes += 1;
                    partidoviewmodel.jugadoresEquipoVisitante.get(buscarPosicionJugadorVisitante(jj)).rebotesDef += 1;

                    desSeleccionarJugador(jugadorvisitante, R.drawable.recyclerv_round_greydark_black);
                });
            });
            j++;
        }

        binding.botonAcabarPartido.setOnClickListener(view16 -> {
            try {
                File f = new GenerarCSV().generarCSV(partidoviewmodel.partido, partidoviewmodel.jugadoresEquipoLocal, partidoviewmodel.jugadoresEquipoVisitante);

                stor.getReference("partidos/"+ f.getName())
                        .putStream(new FileInputStream(f))
                        .continueWithTask(task -> Objects.requireNonNull(task.getResult()).getStorage().getDownloadUrl())
                        .addOnSuccessListener(url -> {
                            partidoviewmodel.partido.archivoCSV = url.toString();
                            subirPartidoFirebase(partidoviewmodel.partido, partidoviewmodel.jugadoresEquipoLocal, partidoviewmodel.jugadoresEquipoVisitante);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Jugador jugador: partidoviewmodel.jugadoresEquipoLocalGeneral) {
                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoLocal)
                        .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).update(jugador.toHashMap(jugador)).addOnSuccessListener(documentReference -> {
                });
            }
            for (Jugador jugador: partidoviewmodel.jugadoresEquipoVisitanteGeneral) {
                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoVisitante)
                        .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).update(jugador.toHashMap(jugador)).addOnSuccessListener(documentReference -> {
                });
            }
            nav.navigate(R.id.action_gameFragment_to_menuFragment);
        });
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

    private void subirPartidoFirebase(Partido partido, List<Jugador> jugadoresEquipoLocal, List<Jugador> jugadoresEquipoVisitante) {
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).add(partido).addOnSuccessListener(documentReference -> {
            String idPartido = documentReference.getId();
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido).update("idPartido", idPartido);

            String timeStamp = new SimpleDateFormat("dd-MM").format(Calendar.getInstance().getTime());

            for (Jugador jugador: jugadoresEquipoLocal) {
                Map<String, Integer> data = new HashMap<>();
                data.put(partidoviewmodel.partido.nombreEquipoVisitante+" "+timeStamp, jugador.puntos);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido).collection(FirebaseVar.JUGADORESLOCALES).add(jugador).addOnSuccessListener(v->{
                    db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoLocal)
                            .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).collection(FirebaseVar.PPP).document(FirebaseVar.PUNTOS).set(data, SetOptions.merge());
                });
            }
            for (Jugador jugador: jugadoresEquipoVisitante) {
                Map<String, Integer> data = new HashMap<>();
                data.put(partidoviewmodel.partido.nombreEquipoVisitante+" "+timeStamp, jugador.puntos);

                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.PARTIDOS).document(idPartido).collection(FirebaseVar.JUGADORESVISITANTES).add(jugador).addOnSuccessListener(v->{
                    db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(partidoviewmodel.partido.idEquipoVisitante)
                            .collection(FirebaseVar.JUGADORES).document(jugador.idJugador).collection(FirebaseVar.PPP).document(FirebaseVar.PUNTOS).set(data, SetOptions.merge());
                });
            }
        });
    }

    private void pasarcuarto() {
        partidoviewmodel.mTimeLeftInMillis = partidoviewmodel.START_TIME_10_IN_MILLIS;
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
        botonesJugadoresAdmin(false);
    }

    private void desSeleccionarJugador(LinearLayout p, int p2) {
        actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitantes, botonesAcciones);
        setBackground(p, p2);
    }

    private void seleccionaJugador(LinearLayout jugador,List<LinearLayout> botonesJugadoresLocales,List<LinearLayout> botonesJugadoresVisitantes) {
        setBackground(jugador, R.drawable.recyclerv_round_white_red);
        botonesJugadoresAdmin(false);
        botonesAccionesAdmin(botonesAcciones, true);
    }

    private void setBackground(LinearLayout p, int p2) {
        p.setBackgroundResource(p2);
    }

    private void actualizarResultado(List<LinearLayout> botonesJugadoresLocales, List<LinearLayout> botonesJugadoresVisitante, List<ImageButton> botonesAcciones) {
        binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));
        binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));

        botonesJugadoresAdmin(true);
        botonesAccionesAdmin(botonesAcciones, false);
    }

    private void botonesJugadoresAdmin(boolean b) {
        for (LinearLayout l: botonesJugadoresLocales) {
            l.setClickable(b);
        }
        for (LinearLayout l: botonesJugadoresVisitantes) {
            l.setClickable(b);
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
            s.setText(String.valueOf(0));
        }
        for (TextView s: puntosJugadoresLocales){
            s.setText(String.valueOf(0));
        }
        for (TextView s: faltasJugadoresLocales){
            s.setText(String.valueOf(0));
        }
    }
    private void printarJugadoresEnNombreVisitante() {
        for (TextView s: nombresJugadoresLocales){
            s.setText("Jugador");
        }
        for (TextView s: dorsalesJugadoresLocales){
            s.setText(String.valueOf(0));
        }
        for (TextView s: puntosJugadoresLocales){
            s.setText(String.valueOf(0));
        }
        for (TextView s: faltasJugadoresLocales){
            s.setText(String.valueOf(0));
        }
    }

    private void printarJugadoresLocal() {
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
            if (partidoviewmodel.jugadoresEquipoLocal.get(i).starter){
                if (binding.nombreA1.getText().equals("Jugador")){
                    binding.nombreA1.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreA2.getText().equals("Jugador")){
                    binding.nombreA2.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreA3.getText().equals("Jugador")){
                    binding.nombreA3.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreA4.getText().equals("Jugador")){
                    binding.nombreA4.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                    binding.puntosA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreA5.getText().equals("Jugador")){
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
                if (binding.nombreB1.getText().equals("Jugador")){
                    binding.nombreB1.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreB2.getText().equals("Jugador")){
                    binding.nombreB2.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreB3.getText().equals("Jugador")){
                    binding.nombreB3.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreB4.getText().equals("Jugador")){
                    binding.nombreB4.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
                }else if (binding.nombreB5.getText().equals("Jugador")){
                    binding.nombreB5.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                    binding.puntosB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).puntos));
                    binding.faltasB1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).faltasCometidas));
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