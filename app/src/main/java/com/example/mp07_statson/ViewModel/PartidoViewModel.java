package com.example.mp07_statson.ViewModel;

import android.os.CountDownTimer;

import androidx.lifecycle.ViewModel;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;

import java.util.List;

public class PartidoViewModel extends ViewModel {
    public List<Jugador> jugadoresEquipoLocal;
    public List<Jugador> jugadoresEquipoVisitante;



    //CRONOMETRO
    public CountDownTimer mCountDownTimer;
    public boolean mTimerRunning;
    public static final long START_TIME_IN_MILLIS = 600000;
    public long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    public Partido partido;

    public int marcadorLocal = 0;
    public int marcadorVisitante = 0;
    public int cuarto = 1;
}
