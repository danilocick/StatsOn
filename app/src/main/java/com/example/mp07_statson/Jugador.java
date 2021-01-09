package com.example.mp07_statson;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Jugador {
    @PrimaryKey @NonNull
        String dorsal;

    String nombre;
    String imagen;
    int puntos, rebotes, asistencias, recuperaciones, perdidas, tapones,
            t1mas, t1menos, t2mas, t2menos, t3mas, t3menos,
            rebotesDef, rebotesOf, faltasRecibidas, faltasCometidas,
            taponesRealizados, taponesRecibidos;

    public Jugador(String nombre, String dorsal, String imagen) {
        this.nombre = nombre;
        this.dorsal = dorsal;
        this.imagen = imagen;
        puntos= 0;
        rebotes= 0;
        asistencias= 0;
        recuperaciones= 0;
        perdidas= 0;
        tapones= 0;
        t1mas= 0;
        t1menos= 0;
        t2mas= 0;
        t2menos= 0;
        t3mas= 0;
        t3menos= 0;
        rebotesDef= 0;
        rebotesOf= 0;
        faltasRecibidas= 0;
        faltasCometidas= 0;
        taponesRealizados= 0;
        taponesRecibidos= 0;
    }

    @NonNull
    public String getDorsal() {
        return dorsal;
    }
    public void setDorsal(@NonNull String dorsal) {
        this.dorsal = dorsal;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getRebotes() {
        return rebotes;
    }
    public void setRebotes(int rebotes) {
        this.rebotes = rebotes;
    }

    public int getAsistencias() {
        return asistencias;
    }
    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getRecuperaciones() {
        return recuperaciones;
    }
    public void setRecuperaciones(int recuperaciones) {
        this.recuperaciones = recuperaciones;
    }

    public int getPerdidas() {
        return perdidas;
    }
    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getTapones() {
        return tapones;
    }
    public void setTapones(int tapones) {
        this.tapones = tapones;
    }

    public int getT1mas() {
        return t1mas;
    }
    public void setT1mas(int t1mas) {
        this.t1mas = t1mas;
    }

    public int getT1menos() {
        return t1menos;
    }
    public void setT1menos(int t1menos) {
        this.t1menos = t1menos;
    }

    public int getT2mas() {
        return t2mas;
    }
    public void setT2mas(int t2mas) {
        this.t2mas = t2mas;
    }

    public int getT2menos() {
        return t2menos;
    }
    public void setT2menos(int t2menos) {
        this.t2menos = t2menos;
    }

    public int getT3mas() {
        return t3mas;
    }
    public void setT3mas(int t3mas) {
        this.t3mas = t3mas;
    }

    public int getT3menos() {
        return t3menos;
    }
    public void setT3menos(int t3menos) {
        this.t3menos = t3menos;
    }

    public int getRebotesDef() {
        return rebotesDef;
    }
    public void setRebotesDef(int rebotesDef) {
        this.rebotesDef = rebotesDef;
    }

    public int getRebotesOf() {
        return rebotesOf;
    }
    public void setRebotesOf(int rebotesOf) {
        this.rebotesOf = rebotesOf;
    }

    public int getFaltasRecibidas() {
        return faltasRecibidas;
    }
    public void setFaltasRecibidas(int faltasRecibidas) {
        this.faltasRecibidas = faltasRecibidas;
    }

    public int getFaltasCometidas() {
        return faltasCometidas;
    }
    public void setFaltasCometidas(int faltasCometidas) {
        this.faltasCometidas = faltasCometidas;
    }

    public int getTaponesRealizados() {
        return taponesRealizados;
    }
    public void setTaponesRealizados(int taponesRealizados) {
        this.taponesRealizados = taponesRealizados;
    }

    public int getTaponesRecibidos() {
        return taponesRecibidos;
    }
    public void setTaponesRecibidos(int taponesRecibidos) {
        this.taponesRecibidos = taponesRecibidos;
    }
}
