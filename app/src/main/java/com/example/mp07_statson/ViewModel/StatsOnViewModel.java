package com.example.mp07_statson.ViewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StatsOnViewModel extends ViewModel {

    public String idEquipoSeleccionado;
    public Uri imagenEquipoSeleccionada;
    public Uri imagenJugadorSeleccionada;

    public LiveData<Object> seleccionado() {
        return null;
    }
}
