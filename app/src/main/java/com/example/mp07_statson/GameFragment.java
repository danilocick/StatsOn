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

import com.example.mp07_statson.ViewModel.NombreRivalViewModel;
import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.example.mp07_statson.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {


    private FragmentGameBinding binding;
    private NavController navController;
    private NombreRivalViewModel nombreRivalViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        nombreRivalViewModel = new ViewModelProvider(requireActivity()).get(NombreRivalViewModel.class);

        nombreRivalViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.titleTeamB.setText(a));
        //AcabarPartido
        binding.botonAcabarPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_gameFragment_to_menuFragment);
            }
        });

        //vista previa
        binding.botonVistaPrevia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_gameFragment_to_outputFragment);
            }
        });
    }
}