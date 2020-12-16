package com.example.mp07_statson;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class JugadoresMiTMViewModel extends AndroidViewModel {
    private final JugadoresMiTMRepositorio jugadoresRepositorio;

    public JugadoresMiTMViewModel(@NonNull Application application) {
        super(application);

        jugadoresRepositorio = new JugadoresMiTMRepositorio(application);
    }

    public void insertar(String nombre, String dorsal, Uri imagenSeleccionada) {
        jugadoresRepositorio.insertar(nombre, dorsal, imagenSeleccionada);
    }
}
