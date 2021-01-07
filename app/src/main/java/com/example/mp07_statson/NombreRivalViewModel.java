package com.example.mp07_statson;

import androidx.lifecycle.ViewModel;

public class NombreRivalViewModel extends ViewModel {
    NombreRival nombreRival = new NombreRival();
    public void setNombreRival(String nombre){
        nombreRival.setNombreRival(nombre);
    }
    public String getNombreRival(){
        return  nombreRival.getNombre();
    }
}
