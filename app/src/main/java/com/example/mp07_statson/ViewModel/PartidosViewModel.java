package com.example.mp07_statson.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.Model.PartidosRepositorio;

import java.util.List;

public class PartidosViewModel extends AndroidViewModel {
    PartidosRepositorio partidosRepositorio;

    MutableLiveData<List<Partido>> listPartidosMutableLiveData = new MutableLiveData<>();

    public PartidosViewModel(@NonNull Application application) {
        super(application);
        partidosRepositorio = new PartidosRepositorio();
        listPartidosMutableLiveData.setValue(partidosRepositorio.obtener());
    }

    public MutableLiveData<List<Partido>> obtener(){
        return listPartidosMutableLiveData;
    }

}
