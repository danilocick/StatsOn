package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentRivalBinding;


public class RivalFragment extends Fragment {

    private NavController navController;
    private FragmentRivalBinding binding;
    private EquipoViewModel Equipo;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRivalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        Equipo = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //insertar nombre rival
        binding.botonSiguienteERival.setOnClickListener(v -> {
            String teamb = binding.nombreRival.getText().toString();

            if(!teamb.equals("")) {
                //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
                Equipo.insertar(teamb);
                //navegamos
                navController.navigate(R.id.action_rivalFragment_to_equipoAFragment);
            }
            else {
                // If name is not entered
                Toast.makeText(requireActivity().getApplicationContext(), "Name is required", Toast.LENGTH_LONG).show();
            }
        });


        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });
     }
}