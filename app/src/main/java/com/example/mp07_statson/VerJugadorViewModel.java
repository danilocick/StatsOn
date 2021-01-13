package com.example.mp07_statson;

import androidx.lifecycle.MutableLiveData;

public class VerJugadorViewModel {

    MutableLiveData<Jugador> jugadorMutableLiveData = new MutableLiveData<>();

    void seleccionar(Jugador jugador){
        jugadorMutableLiveData.setValue(jugador);
    }

    MutableLiveData<Jugador> seleccionado(){
        return jugadorMutableLiveData;
    }
}
