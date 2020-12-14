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
import com.example.mp07_statson.databinding.FragmentOptionsBinding;

public class OptionsFragment extends Fragment {
    Button botonComebackOpc;
    Button botonGuardarOpc;

    private NavController navController;
    private FragmentOptionsBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOptionsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackOpc = view.findViewById(R.id.botonComeBackOpc);
        botonGuardarOpc = view.findViewById(R.id.botonGuardarOpc);

        //ComeBack
        botonComebackOpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_optionsFragment_to_menuFragment);
            }
        });

        //Crear Jugador
        botonGuardarOpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_optionsFragment_to_menuFragment);
            }
        });

    }
}