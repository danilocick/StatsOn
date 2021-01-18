package com.example.mp07_statson.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NombreRivalViewModel extends ViewModel {
    public MutableLiveData<String> nombreMutableLiveData = new MutableLiveData<>();

    public void seleccionar(String nombre){
        nombreMutableLiveData.setValue(nombre);
    }

    public MutableLiveData<String> seleccionado(){
        return nombreMutableLiveData;
    }
}
