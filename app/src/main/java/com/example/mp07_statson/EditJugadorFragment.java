package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp07_statson.ViewModel.VerJugadorViewModel;
import com.example.mp07_statson.databinding.FragmentEditJugadorBinding;

public class EditJugadorFragment extends Fragment {


    private FragmentEditJugadorBinding binding;
    private NavController navController;
    private VerJugadorViewModel verJugadorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEditJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        verJugadorViewModel = new ViewModelProvider(requireActivity()).get(VerJugadorViewModel.class);

        verJugadorViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.nombreJugador.setText(a.nombre));
        verJugadorViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.dorsalJugador.setText(a.dorsal));
        //verJugadorViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.imagenJugadorMiTeam.);



        //ComeBack
        binding.botonComeBackERival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //ComeBack
        binding.botonCrearAddJTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });


    }


}