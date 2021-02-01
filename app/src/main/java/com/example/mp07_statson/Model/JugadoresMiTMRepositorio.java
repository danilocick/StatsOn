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

    public void insertar(String nombre, int dorsal, Uri imagenSeleccionada, int idEquipo) {
        executor.execute(() -> {
            jugadoresDao.insertar(new Jugador(nombre,dorsal,imagenSeleccionada.toString(), idEquipo));
        });
    }

    public void actualizar(Jugador jugador) {
        executor.execute(() -> {
            jugadoresDao.actualizar(jugador);
        });
    }

    public void delete(Jugador jugador) {
        executor.execute(() -> {
            jugadoresDao.delete(jugador);
        });
    }
    public LiveData<List<Jugador>> obtenerLocal() {
        return jugadoresDao.obtenerLocal();
    }

    public LiveData<List<Jugador>> obtenerVisitante() {
        return jugadoresDao.obtenerVisitante();
    }
}
