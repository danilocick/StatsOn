package com.example.mp07_statson.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PartidosRepositorio {
    List<Partido> partidos = new ArrayList<Partido>();
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatosMiTM.PartidosDao partidosDao;

    public PartidosRepositorio(Application application){
        partidosDao = BaseDeDatosMiTM.getInstance(application).obtenerPartidosDao();
    }

    public void insertar(Partido partido) {
        executor.execute(() -> {
            partidosDao.insertar(partido);
        });
    }

    public void delete(Partido partido) {
        executor.execute(() -> {
            partidosDao.delete(partido);
        });
    }
    public LiveData<List<Partido>> obtener() {
        return partidosDao.obtener();
    }
}

