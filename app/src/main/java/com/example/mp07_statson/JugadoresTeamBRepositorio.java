package com.example.mp07_statson;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JugadoresTeamBRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatosTeamB.JugadoresTeamBDao jugadoresTeamBDao;

    public JugadoresTeamBRepositorio(Application application) {
        jugadoresTeamBDao = BaseDeDatosTeamB.getInstance(application).obtenerJugadoresTeamBDao();
    }

    public void insertar(String nombre, String dorsal, Uri imagenSeleccionada) {
        executor.execute(()->{
            jugadoresTeamBDao.insertar(new Jugador(nombre,dorsal,imagenSeleccionada.toString()));
        });
    }

    public LiveData<List<Jugador>> obtener() {
        return jugadoresTeamBDao.obtener();
    }
}
