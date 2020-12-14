package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mp07_statson.databinding.FragmentEquipoBBinding;


public class EquipoBFragment extends Fragment {

    Button botonSiguienteTeamB;
    Button botonComebackTeamB;
    Button botonAnyadirJugador;

    private NavController navController;
    private FragmentEquipoBBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEquipoBBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackTeamB = view.findViewById(R.id.botonComeBackTeamB);
        botonAnyadirJugador = view.findViewById(R.id.botonanyadirjugador);
        botonSiguienteTeamB = view.findViewById(R.id.botonIniciarPartidoTeamB);

        //Siguiente
        botonSiguienteTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoBFragment_to_gameFragment);
            }
        });

        //ComeBack
        botonComebackTeamB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoBFragment_to_equipoAFragment);
            }
        });

        //Ir anyadirjugador
        botonAnyadirJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoBFragment_to_addJugadorFragment);
            }
        });

    }
}