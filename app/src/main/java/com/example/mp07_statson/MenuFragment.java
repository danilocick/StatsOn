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

import com.example.mp07_statson.databinding.FragmentLoadBinding;
import com.example.mp07_statson.databinding.FragmentMenuBinding;

public class MenuFragment extends Fragment {

    Button botonPartidoNuevo;
    Button botonResultados;
    Button botonMiEquipo;
    Button botonOpciones;
    NavController navController;
    private FragmentMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        botonPartidoNuevo = view.findViewById(R.id.botonPartidoNuevo);
        botonResultados = view.findViewById(R.id.botonResultados);
        botonMiEquipo= view.findViewById(R.id.botonMiEquipo);
        botonOpciones = view.findViewById(R.id.botonOpciones);

        botonPartidoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuFragment_to_rivalFragment);
            }
        });

        botonResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuFragment_to_listaPartidosFragment);
            }
        });

        botonMiEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuFragment_to_resultadoMenuFragment);
            }
        });

        botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_menuFragment_to_optionsFragment);
            }
        });
    }
}