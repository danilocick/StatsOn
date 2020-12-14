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

import com.example.mp07_statson.databinding.FragmentEquipoABinding;
import com.example.mp07_statson.databinding.FragmentRivalBinding;


public class EquipoAFragment extends Fragment {

    Button botonSiguienteTeamA;
    Button botonComebackTeamA;

    private NavController navController;
    private FragmentEquipoABinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEquipoABinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackTeamA = view.findViewById(R.id.botonComeBackTeamA);
        botonSiguienteTeamA = view.findViewById(R.id.botonSiguienteTeamA);

        botonSiguienteTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoAFragment_to_equipoBFragment);
            }
        });

        botonComebackTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoAFragment_to_rivalFragment);
            }
        });
    }
}