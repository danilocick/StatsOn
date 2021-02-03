package com.example.mp07_statson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.EquipoRepositorio;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.Model.PartidosRepositorio;

import java.util.List;

public class PartidosViewModel extends AndroidViewModel {
    private final PartidosRepositorio partidosRepositorio;

    public PartidosViewModel(@NonNull Application application) {
        super(application);

        partidosRepositorio = new PartidosRepositorio(application);
    }

    //INSERT
    public void insertar(Partido partido) {
        partidosRepositorio.insertar(partido);
    }

    //DELETES
    public void delete(Partido partido) {
        partidosRepositorio.delete(partido);
    }

    //OBTENER
    public LiveData<List<Partido>> obtener() {
       return partidosRepositorio.obtener();
    }


    //MUTABLE LIVE DATA
    public void seleccionar(Partido partido) {

    }
}
