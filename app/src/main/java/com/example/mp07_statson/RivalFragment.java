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

import com.example.mp07_statson.databinding.FragmentMenuBinding;
import com.example.mp07_statson.databinding.FragmentRivalBinding;


public class RivalFragment extends Fragment {

    Button botonSiguienteERival;
    Button botonComebackERival;

    private NavController navController;
    private FragmentRivalBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRivalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackERival = view.findViewById(R.id.botonComeBackERival);
        botonSiguienteERival = view.findViewById(R.id.botonSiguienteERival);

        botonSiguienteERival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_rivalFragment_to_equipoAFragment);
            }
        });

        botonComebackERival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_rivalFragment_to_menuFragment);
            }
        });
    }

}