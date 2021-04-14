package com.example.mp07_statson.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Jugador;

import java.util.List;

public class JugadoresViewModel extends AndroidViewModel {
    MutableLiveData<Jugador> jugadorMutableLiveData = new MutableLiveData<>();
    public Uri imagenSeleccionada;

    public JugadoresViewModel(@NonNull Application application) {
        super(application);
    }

    //MUTABLE
    public void seleccionar(Jugador jugador){
        jugadorMutableLiveData.setValue(jugador);
    }

    public MutableLiveData<Jugador> seleccionado(){
        return jugadorMutableLiveData;
    }
}
