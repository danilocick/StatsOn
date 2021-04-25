package com.example.mp07_statson.ViewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mp07_statson.Model.Jugador;

public class StatsOnViewModel extends ViewModel {

    //equipos
    public String idEquipoSeleccionado;
    public Uri imagenEquipoSeleccionada;

    //jugadores
    public Uri imagenJugadorSeleccionada;
    public Jugador jugadorSeleccionado;

    //Opciones de partido
    public int minutos;
    public int periodos;
    public int minutosPE;



}
