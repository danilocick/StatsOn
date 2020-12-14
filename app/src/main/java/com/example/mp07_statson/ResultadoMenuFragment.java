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
import com.example.mp07_statson.databinding.FragmentResultadoMenuBinding;

public class ResultadoMenuFragment extends Fragment {

    Button botonComebackMiTM;
    Button botonAnyadirJugadorMiTM;

    private NavController navController;
    private FragmentResultadoMenuBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentResultadoMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackMiTM = view.findViewById(R.id.botonComeBackMiTM);
        botonAnyadirJugadorMiTM = view.findViewById(R.id.botonanyadirjugadorMiTM);

        //ComeBack
        botonComebackMiTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_resultadoMenuFragment_to_menuFragment);
            }
        });

        //Ir anyadirjugador
        botonAnyadirJugadorMiTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_resultadoMenuFragment_to_addJugadorMTFragment);
            }
        });

    }
}