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
    public String id_equipo_seleccionado;
    public boolean rival;

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

    public String getNombreRival() {
        return nombreRival;
    }

    public void setNombreRival(String nombreRival) {
        this.nombreRival = nombreRival;
    }

    public String getId_equipo_seleccionado() {
        return id_equipo_seleccionado;
    }

    public void setId_equipo_seleccionado(String id_equipo_seleccionado) {
        this.id_equipo_seleccionado = id_equipo_seleccionado;
    }

    public boolean isRival() {
        return rival;
    }

    public void setRival(boolean rival) {
        this.rival = rival;
    }

    public MutableLiveData<Equipo> getJugadorMutableLiveData() {
        return jugadorMutableLiveData;
    }

    public void setJugadorMutableLiveData(MutableLiveData<Equipo> jugadorMutableLiveData) {
        this.jugadorMutableLiveData = jugadorMutableLiveData;
    }

    public Uri getImagenSeleccionada() {
        return imagenSeleccionada;
    }

    public void setImagenSeleccionada(Uri imagenSeleccionada) {
        this.imagenSeleccionada = imagenSeleccionada;
    }
}
