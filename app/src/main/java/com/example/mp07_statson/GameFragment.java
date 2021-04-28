package com.example.mp07_statson;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentGameBinding;

import java.util.Locale;

import static com.example.mp07_statson.R.layout.popup_asistencia;

public class GameFragment extends BaseFragment {

    private FragmentGameBinding binding;
    private int marcadorLocal = 0;
    private int marcadorVisitante = 0;
    private int cuarto = 0;

    /*https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.start.setOnClickListener(view17 ->{
                if (partidoviewmodel.mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
        });
        updateCountDownText();


        binding.botonAcabarPartido.setOnClickListener(view16 -> nav.navigate(R.id.action_gameFragment_to_menuFragment));

        binding.botonVistaPrevia.setOnClickListener(view15 -> nav.navigate(R.id.action_gameFragment_to_outputMatchesFragment));


        binding.jugB1.setOnClickListener(v -> {
            binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_white_red);

            binding.imagenThreePointMore.setOnClickListener(view1 -> {
                //popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER, 0, 0);

                marcadorVisitante+=3;
                String str = String.valueOf(marcadorVisitante);
                binding.marcadorVisitante.setText(str);
                //TODO:ONCLICK JUGADOR, return normal backgound
                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });

            binding.imagenThreePointLess.setOnClickListener(view1 -> {
                //popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER, 0, 0);
                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });
        });

        binding.jugA1.setOnClickListener(v -> {
            binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_white_red);
            binding.imagenThreePointMore.setOnClickListener(view1 -> {
                //popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER, 0, 0);
                marcadorLocal+=3;
                String str = String.valueOf(marcadorLocal);
                binding.marcadorLocal.setText(str);
                //TODO:ONCLICK JUGADOR, return normal backgound
                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });

            binding.imagenThreePointLess.setOnClickListener(view1 -> {
                //popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER, 0, 0);
                binding.jugA1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });
        });
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