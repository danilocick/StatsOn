package com.example.mp07_statson;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Jugador;

public class VerJugadorViewModel extends AndroidViewModel {

    MutableLiveData<Jugador> jugadorMutableLiveData = new MutableLiveData<>();

    public VerJugadorViewModel(@NonNull Application application) {
        super(application);
    }

    void seleccionar(Jugador jugador){
        jugadorMutableLiveData.setValue(jugador);
    }

    MutableLiveData<Jugador> seleccionado(){
        return jugadorMutableLiveData;
    }
}
