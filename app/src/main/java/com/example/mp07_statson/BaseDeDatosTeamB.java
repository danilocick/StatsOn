package com.example.mp07_statson;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Jugador.class},version = 1, exportSchema = false)
public abstract class BaseDeDatosTeamB extends RoomDatabase {

    private static volatile BaseDeDatosTeamB db;

    public abstract JugadoresTeamBDao obtenerJugadoresTeamBDao();

    public static BaseDeDatosTeamB getInstance(final Context context){
        if (db==null){
            synchronized (BaseDeDatosTeamB.class){
                if (db==null){
                    db= Room.databaseBuilder(context, BaseDeDatosTeamB.class,"app2.dbb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return db;
    }

    @Dao
    interface JugadoresTeamBDao{
        @Insert
        void insertar(Jugador jugador);

        @Query("SELECT * FROM Jugador")
        LiveData<List<Jugador>> obtener();

        @Delete
        void delete(Jugador jugador);
    }
}
