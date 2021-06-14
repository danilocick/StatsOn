package com.example.mp07_statson.ViewModel;

import android.os.CountDownTimer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mp07_statson.Model.Accion;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class PartidoViewModel extends ViewModel {
    public List<Jugador> jugadoresEquipoLocal;
    public List<Jugador> jugadoresEquipoVisitante;

    public List<Jugador> jugadoresEquipoLocalGeneral;
    public List<Jugador> jugadoresEquipoVisitanteGeneral;
    public boolean seleccionEquipo;

    public MutableLiveData<Boolean> repintarEquipoLocal = new MutableLiveData<>();
    public MutableLiveData<Boolean> repintarEquipoVisitante = new MutableLiveData<>();
    public MutableLiveData<Boolean> anyadir_segundos = new MutableLiveData<>();


    //CRONOMETRO
    public CountDownTimer mCountDownTimer;
    public boolean mTimerRunning;
    public static final long START_TIME_10_IN_MILLIS = 600000;
    public static final long START_TIME_5_IN_MILLIS = 300000;
    public static final long START_TIME_6_IN_MILLIS = 360000;
    public long mTimeLeftInMillis;

    public int segundo_sumado;

    public Partido partido;
    public int cuarto = 1;
    public LinkedList<Accion> acciones;
}