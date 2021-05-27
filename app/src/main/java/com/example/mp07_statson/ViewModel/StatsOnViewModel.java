package com.example.mp07_statson.ViewModel;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.Model.Ppp;

import java.util.List;

public class StatsOnViewModel extends ViewModel {

    //equipos
    public String idEquipoSeleccionado;
    public Uri imagenEquipoSeleccionada;
    public String nombreEquipoSeleccionado;

    //jugadores
    public Uri imagenJugadorSeleccionada;
    public Jugador jugadorSeleccionado;
    public MutableLiveData<Boolean> actualizarStatsIndividuales = new MutableLiveData<>();
    public List<Ppp> pppJugador;


    //Opciones de partido
    public int minutos;
    public int periodos;
    public int minutosPE;


    //Seleccion partido nuevo
    public String idEquipoLocal;
    public String idEquipoVisitante;
    public String nombreEquipoLocal;
    public String nombreEquipoVisitante;

    public Partido partido;

    public String imagenEquipoVisitante;
    public String imagenEquipoLocal;
}