package com.example.mp07_statson.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.JugadoresTeamBRepositorio;

import java.util.List;

public class JugadoresTeamBViewModel extends AndroidViewModel {
    private final JugadoresTeamBRepositorio jugadoresTeamBRepositorio;

    public JugadoresTeamBViewModel(@NonNull Application application){
        super(application);
        jugadoresTeamBRepositorio = new JugadoresTeamBRepositorio(application);
    }

    public void insertar(String nombre, String dorsal, Uri imagenSeleccionada) {
        jugadoresTeamBRepositorio.insertar(nombre,dorsal,imagenSeleccionada);
    }

    public LiveData<List<Jugador>> obtener() {
        return jugadoresTeamBRepositorio.obtener();
    }

    public void delete(Jugador jugador) {
        jugadoresTeamBRepositorio.delete(jugador);
    }
}
