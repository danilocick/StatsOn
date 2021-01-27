package com.example.mp07_statson.Model;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JugadoresMiTMRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatosMiTM.JugadoresDao jugadoresDao;

    public JugadoresMiTMRepositorio(Application application){
        jugadoresDao = BaseDeDatosMiTM.getInstance(application).obtenerJugadoresDao();
    }

    public void insertar(String nombre, String dorsal, Uri imagenSeleccionada) {
        executor.execute(() -> {
            jugadoresDao.insertar(new Jugador(nombre,dorsal,imagenSeleccionada.toString()));
        });
    }

    public void delete(Jugador jugador) {
        executor.execute(() -> {
            jugadoresDao.delete(jugador);
        });
    }

    public LiveData<List<Jugador>> obtener() {
        return jugadoresDao.obtener();
    }

    public void actualizar(String nombre, String dorsal, Uri imagenSeleccionada) {
        executor.execute(() -> {
            jugadoresDao.actualizar(new Jugador(nombre,dorsal,imagenSeleccionada.toString()));
        });
    }
}
