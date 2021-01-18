package com.example.mp07_statson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Jugador;

public class VerJugadorViewModel extends AndroidViewModel {

    public MutableLiveData<Jugador> jugadorMutableLiveData = new MutableLiveData<>();

    public VerJugadorViewModel(@NonNull Application application) {
        super(application);
    }

    public void seleccionar(Jugador jugador){
        jugadorMutableLiveData.setValue(jugador);
    }

    public MutableLiveData<Jugador> seleccionado(){
        return jugadorMutableLiveData;
    }
}
