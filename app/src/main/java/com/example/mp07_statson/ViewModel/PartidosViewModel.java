package com.example.mp07_statson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Partido;

import java.util.List;

public class PartidosViewModel extends AndroidViewModel {

    public PartidosViewModel(@NonNull Application application) {
        super(application);
    }

    //MUTABLE LIVE DATA
    public MutableLiveData<Partido> jugadorMutableLiveData = new MutableLiveData<>();

    public void seleccionar(Partido partido){
        jugadorMutableLiveData.setValue(partido);
    }
    public MutableLiveData<Partido> seleccionado(){
        return jugadorMutableLiveData;
    }
}
