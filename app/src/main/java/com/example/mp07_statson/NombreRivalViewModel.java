package com.example.mp07_statson;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NombreRivalViewModel extends ViewModel {
    MutableLiveData<String> nombreMutableLiveData = new MutableLiveData<>();

    void seleccionar(String nombre){
        nombreMutableLiveData.setValue(nombre);
    }

    MutableLiveData<String> seleccionado(){
        return nombreMutableLiveData;
    }
}
