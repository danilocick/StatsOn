package com.example.mp07_statson.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.Jugador;

public class EquipoViewModel extends AndroidViewModel {
    public String nombreRival;
    MutableLiveData<Equipo> jugadorMutableLiveData = new MutableLiveData<>();
    public Uri imagenSeleccionada;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
    }

    //MUTABLE
    public void seleccionar(Equipo equipo){
        jugadorMutableLiveData.setValue(equipo);
    }

    public MutableLiveData<Equipo> seleccionado(){
        return jugadorMutableLiveData;
    }
}
