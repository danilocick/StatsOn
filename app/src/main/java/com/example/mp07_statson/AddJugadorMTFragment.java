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

import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.example.mp07_statson.databinding.FragmentAddJugadorMTBinding;


public class AddJugadorMTFragment extends Fragment {

    Button botonComebackAddJTM;
    Button botonCrearAddJTM;

    private NavController navController;
    private FragmentAddJugadorMTBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorMTBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackAddJTM = view.findViewById(R.id.botonComeBackAddJTM);
        botonCrearAddJTM = view.findViewById(R.id.botonCrearAddJTM);

        //ComeBack
        botonComebackAddJTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorMTFragment_to_resultadoMenuFragment);
            }
        });

        //Crear Jugador
        botonCrearAddJTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorMTFragment_to_resultadoMenuFragment);
            }
        });

    }
}