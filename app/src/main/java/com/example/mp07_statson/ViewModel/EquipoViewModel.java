package com.example.mp07_statson.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.EquipoRepositorio;
import com.example.mp07_statson.Model.Jugador;

import java.util.List;

public class EquipoViewModel extends AndroidViewModel {
    private final EquipoRepositorio equiposRepositorio;

    public EquipoViewModel(@NonNull Application application) {
        super(application);

        equiposRepositorio = new EquipoRepositorio(application);
    }

    //INSERT
    public void insertar(Equipo equipo) {
        equiposRepositorio.insertar(equipo);
    }

    //DELETES
    public void delete(Equipo equipo) {
        equiposRepositorio.delete(equipo);
    }

    //GETTERS
    public LiveData<List<Equipo>> obtenerLocal() {
        return equiposRepositorio.obtener();
    }
}
