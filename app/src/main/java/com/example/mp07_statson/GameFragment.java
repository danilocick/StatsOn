package com.example.mp07_statson;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.databinding.FragmentGameBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class GameFragment extends BaseFragment {

    private FragmentGameBinding binding;

    /*https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<LinearLayout> botonesJugadoresLocales = Arrays.asList(binding.jugA1, binding.jugA2, binding.jugA3, binding.jugA4, binding.jugA5);
        List<LinearLayout> botonesJugadoresVisitante = Arrays.asList(binding.jugB1, binding.jugB2, binding.jugB3, binding.jugB4, binding.jugB5);
        List<ImageButton> botonesAcciones = Arrays.asList(binding.imagenThreePointMore, binding.imagenThreePointLess, binding.imagenTwoPointMore, binding.imagenTwoPointLess, binding.imagenFreeThrowMore,
                binding.imagenFreeThrowLess, binding.imagenAsistencia, binding.imagenTaponCometido, binding.imagenTaponRecibido, binding.imagenRobo, binding.imagenPerdida,
                binding.imagenFaltaRecibida, binding.imagenFaltaCometida, binding.imagenReboteOfe, binding.imagenReboteDef);

        partidoviewmodel.partido = new Partido();

        binding.start.setOnClickListener(view17 ->{
                if (partidoviewmodel.mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
        });
        updateCountDownText();

        printarJugadoresLocal();
        printarJugadoresVisitante();

        binding.equipolocal.setText(viewmodel.nombreEquipoLocal);
        binding.equipovisitante.setText(viewmodel.nombreEquipoVisitante);

        binding.botonAcabarPartido.setOnClickListener(view16 -> nav.navigate(R.id.action_gameFragment_to_menuFragment));
        binding.botonVistaPrevia.setOnClickListener(view15 -> nav.navigate(R.id.action_gameFragment_to_outputMatchesFragment));

        botonesAccionesAdmin(botonesAcciones,false);

        binding.jugA1.setOnClickListener(v -> {
            binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_white_red);

            botonesJugadoresAdmin(botonesJugadoresLocales, botonesJugadoresVisitante, false);
            binding.jugA1.setClickable(true);
            botonesAccionesAdmin(botonesAcciones, true);

            binding.imagenThreePointMore.setOnClickListener(view1 -> {
                partidoviewmodel.partido.puntosLocal+=3;
                partidoviewmodel.partido.t3masLocal+=1;

                //sumar al jugador

                binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenThreePointLess.setOnClickListener(view1 -> {
                partidoviewmodel.partido.t3menosLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenTwoPointMore.setOnClickListener(view1 -> {
                partidoviewmodel.partido.puntosLocal+=2;
                partidoviewmodel.partido.t2masLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenTwoPointLess.setOnClickListener(view1 -> {
                partidoviewmodel.partido.t2menosLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenFreeThrowMore.setOnClickListener(view1 -> {
                partidoviewmodel.partido.puntosLocal+=1;
                partidoviewmodel.partido.t1masLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);


                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenFreeThrowLess.setOnClickListener(view1 -> {
                partidoviewmodel.partido.t1menosLocal+=1;
                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenAsistencia.setOnClickListener(view1 -> {
                partidoviewmodel.partido.asistenciasLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenTaponCometido.setOnClickListener(view1 -> {
                partidoviewmodel.partido.taponesRealizadosLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenTaponRecibido.setOnClickListener(view1 -> {
                partidoviewmodel.partido.taponesRecibidosLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenRobo.setOnClickListener(view1 -> {
                partidoviewmodel.partido.robosLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenPerdida.setOnClickListener(view1 -> {
                partidoviewmodel.partido.perdidasLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenFaltaRecibida.setOnClickListener(view1 -> {
                partidoviewmodel.partido.faltasRecibidasLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenFaltaCometida.setOnClickListener(view1 -> {
                partidoviewmodel.partido.faltasCometidasLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenReboteOfe.setOnClickListener(view1 -> {
                partidoviewmodel.partido.rebotesOfLocal+=1;
                partidoviewmodel.partido.rebotesLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });

            binding.imagenReboteDef.setOnClickListener(view1 -> {
                partidoviewmodel.partido.rebotesDefLocal+=1;
                partidoviewmodel.partido.rebotesLocal+=1;

                //sumar al jugador

                actualizarResultado(botonesJugadoresLocales, botonesJugadoresVisitante, botonesAcciones);

                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_black);
            });
        });

        binding.jugB1.setOnClickListener(v -> {
            binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_white_red);

            botonesJugadoresAdmin(botonesJugadoresLocales, botonesJugadoresVisitante, false);
            binding.jugB1.setClickable(true);
            botonesAccionesAdmin(botonesAcciones, true);

//            binding.jugB1.setOnClickListener(v1 -> {
//                botonesJugadoresAdmin(botonesJugadoresLocales, botonesJugadoresVisitante, true);
//                botonesAccionesAdmin(botonesAcciones, false);
//
//                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
//            });

            binding.imagenThreePointMore.setOnClickListener(view1 -> {
                partidoviewmodel.partido.puntosVisitante+=3;

                //sumar al jugador

                binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));

                botonesJugadoresAdmin(botonesJugadoresLocales, botonesJugadoresVisitante, true);
                botonesAccionesAdmin(botonesAcciones, false);

                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });


        });
    }

    private void actualizarResultado(List<LinearLayout> botonesJugadoresLocales, List<LinearLayout> botonesJugadoresVisitante, List<ImageButton> botonesAcciones) {
        binding.marcadorLocal.setText(String.valueOf(partidoviewmodel.partido.puntosLocal));
        binding.marcadorVisitante.setText(String.valueOf(partidoviewmodel.partido.puntosVisitante));

        botonesJugadoresAdmin(botonesJugadoresLocales, botonesJugadoresVisitante, true);
        botonesAccionesAdmin(botonesAcciones, false);
    }

    private void botonesJugadoresAdmin(List<LinearLayout> botonesJugadoresLocales, List<LinearLayout> botonesJugadoresVisitante, boolean b) {
        for (int i = 0; i < botonesJugadoresLocales.size(); i++) {
            botonesJugadoresLocales.get(i).setClickable(b);
            botonesJugadoresVisitante.get(i).setClickable(b);
        }
    }

    private void botonesAccionesAdmin(List<ImageButton> botonesAcciones, boolean b) {
        for (int i = 0; i < botonesAcciones.size(); i++) {
            botonesAcciones.get(i).setClickable(b);
        }
    }


    private void printarJugadoresLocal() {
        for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
            if (partidoviewmodel.jugadoresEquipoLocal.get(i).starter){
                if (binding.nombreA1.getText().equals("Jugador")){
                    binding.nombreA1.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA1.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                }else if (binding.nombreA2.getText().equals("Jugador")){
                    binding.nombreA2.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                }else if (binding.nombreA3.getText().equals("Jugador")){
                    binding.nombreA3.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                }else if (binding.nombreA4.getText().equals("Jugador")){
                    binding.nombreA4.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
                }else if (binding.nombreA5.getText().equals("Jugador")){
                    binding.nombreA5.setText(partidoviewmodel.jugadoresEquipoLocal.get(i).nombre);
                    binding.dorsalA5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoLocal.get(i).dorsal));
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
                }else if (binding.nombreB2.getText().equals("Jugador")){
                    binding.nombreB2.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB2.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                }else if (binding.nombreB3.getText().equals("Jugador")){
                    binding.nombreB3.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB3.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                }else if (binding.nombreB4.getText().equals("Jugador")){
                    binding.nombreB4.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB4.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
                }else if (binding.nombreB5.getText().equals("Jugador")){
                    binding.nombreB5.setText(partidoviewmodel.jugadoresEquipoVisitante.get(i).nombre);
                    binding.dorsalB5.setText(String.valueOf(partidoviewmodel.jugadoresEquipoVisitante.get(i).dorsal));
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

}