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
import com.example.mp07_statson.databinding.FragmentEquipoBBinding;

public class AddJugadorFragment extends Fragment {

    Button botonComebackAddJ;
    Button botonCrearAddJ;

    private NavController navController;
    private FragmentAddJugadorBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        botonComebackAddJ = view.findViewById(R.id.botonComeBackAddJ);
        botonCrearAddJ = view.findViewById(R.id.botonCrearAddJ);

        //ComeBack
        botonComebackAddJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorFragment_to_equipoBFragment);
            }
        });

        //Crear Jugador
        botonCrearAddJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorFragment_to_equipoBFragment);
            }
        });

    }
}