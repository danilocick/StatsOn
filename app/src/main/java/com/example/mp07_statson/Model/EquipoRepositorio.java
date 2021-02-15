package com.example.mp07_statson.Model;

import android.app.Application;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EquipoRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatosMiTM.EquiposDao equiposDao;

    public EquipoRepositorio(Application application){
        equiposDao = BaseDeDatosMiTM.getInstance(application).obtenerEquiposDao();
    }

    public void insertar(Equipo equipo) {
        executor.execute(() -> {
            equiposDao.insertar(equipo);
        });
    }

    public void delete(Equipo equipo) {
        executor.execute(() -> {
            equiposDao.delete(equipo);
        });
    }
    public Equipo obtener(int m) {
        return equiposDao.obtener(m);
    }
}
