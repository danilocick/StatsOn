package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentGameBinding;

import java.util.Locale;

import static com.example.mp07_statson.R.layout.popup_asistencia;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private NavController navController;
    private int marcadorLocal = 0;
    private int marcadorVisitante = 0;
    private int cuarto = 0;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private static final long START_TIME_IN_MILLIS = 600000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private EquipoViewModel equipoViewModel;
    /*https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        //Load RivalName
        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);
        int m = 4;
        equipoViewModel.obtener(m).observe(getViewLifecycleOwner(), nombreEquipo -> binding.equipoB.setText(nombreEquipo));

        //Load PopUps
        View popupViewAsistencia = LayoutInflater.from(getActivity()).inflate(popup_asistencia, null);
        //POPUP
        final PopupWindow popupWindowAsistencia = new PopupWindow(popupViewAsistencia, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindowAsistencia.setOutsideTouchable(true);
        popupWindowAsistencia.setFocusable(true);

        binding.start.setOnClickListener(view17 ->{
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
        });
        updateCountDownText();


        //AcabarPartido
        binding.botonAcabarPartido.setOnClickListener(view16 -> navController.navigate(R.id.action_gameFragment_to_menuFragment));
        //vista previa
        binding.botonVistaPrevia.setOnClickListener(view15 -> navController.navigate(R.id.action_gameFragment_to_outputMatchesFragment));


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
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                binding.chronometer.setVisibility(View.INVISIBLE);
            }
        }.start();
        mTimerRunning = true;
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        binding.chronometer.setText(timeLeftFormatted);
    }

}