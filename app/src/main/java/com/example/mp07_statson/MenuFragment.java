package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentMenuBinding;

public class MenuFragment extends BaseFragment {

    private FragmentMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonPartidoNuevo.setOnClickListener(view14 -> nav.navigate(R.id.action_menuFragment_to_rivalFragment));

        binding.botonResultados.setOnClickListener(view13 -> nav.navigate(R.id.action_menuFragment_to_listaPartidosFragment));

//        binding.botonMiEquipo.setOnClickListener(view13 ->  nav.navigate(R.id.action_menuFragment_to_resultadoMenuFragment););

        binding.botonRivales.setOnClickListener(view12 -> nav.navigate(R.id.action_menuFragment_to_rivalesFragment));

        binding.botonOpciones.setOnClickListener(view1 -> nav.navigate(R.id.action_menuFragment_to_optionsFragment));
    }
}