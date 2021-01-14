package com.example.mp07_statson;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mp07_statson.Model.Equipo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EquiposRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatosMiTM.ListaEquiposDao listaEquiposDao;

    public EquiposRepositorio(Application application){
        listaEquiposDao = BaseDeDatosMiTM.getInstance(application).obtenerEquiposDao();
    }

    public void insertar(int idEquipo, String nombre) {
        executor.execute(() -> {
            listaEquiposDao.insertar(new Equipo(idEquipo,nombre));
        });
    }

    public void delete(Equipo equipo) {
        executor.execute(() -> {
            listaEquiposDao.delete(equipo);
        });
    }

    public LiveData<List<Equipo>> obtener() {
        return listaEquiposDao.obtener();
    }

}
