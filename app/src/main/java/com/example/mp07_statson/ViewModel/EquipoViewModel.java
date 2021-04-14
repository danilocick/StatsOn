package com.example.mp07_statson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mp07_statson.Model.Equipo;

public class EquipoViewModel extends AndroidViewModel {
    public String nombreRival;

    public EquipoViewModel(@NonNull Application application) {
        super(application);
    }

    public void seleccionar(Equipo jugador) {
    }
}
