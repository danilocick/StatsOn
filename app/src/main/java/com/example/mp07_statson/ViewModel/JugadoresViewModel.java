package com.example.mp07_statson.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.JugadoresRepositorio;

import java.util.List;

public class JugadoresViewModel extends AndroidViewModel {
    private final JugadoresRepositorio jugadoresRepositorio;
    MutableLiveData<Jugador> jugadorMutableLiveData = new MutableLiveData<>();
    public Uri imagenSeleccionada;

    public JugadoresViewModel(@NonNull Application application) {
        super(application);

        jugadoresRepositorio = new JugadoresRepositorio(application);
    }

    //INSERTS
    public void insertar(String nombre, int dorsal, String imagenSeleccionada, int i) {
        jugadoresRepositorio.insertar(nombre, dorsal, imagenSeleccionada, i);
    }

    //DELETES
    public void delete(Jugador jugador) {
        jugadoresRepositorio.delete(jugador);
    }

    //ACTUALIZAR
    public void actualizar(Jugador jugador) {
        jugadoresRepositorio.actualizar(jugador);
    }

    //GETTERS
    public LiveData<List<Jugador>> obtenerJugadoresDeEquipo(int m) {
        return jugadoresRepositorio.obtenerJugadoresDeEquipo(m);
    }
    //MUTABLE
    public void seleccionar(Jugador jugador){
        jugadorMutableLiveData.setValue(jugador);
    }

    public MutableLiveData<Jugador> seleccionado(){
        return jugadorMutableLiveData;
    }
}
