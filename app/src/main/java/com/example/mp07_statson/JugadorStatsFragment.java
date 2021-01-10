package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.databinding.FragmentAddJugadorMTBinding;
import com.example.mp07_statson.databinding.FragmentJugadorStatsBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;

public class JugadorStatsFragment extends Fragment {

    private NavController navController;
    private FragmentJugadorStatsBinding binding;
    private JugadoresMiTMViewModel jugadoresViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentJugadorStatsBinding.inflate(inflater, container, false)).getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresMiTMViewModel.class);

        //MutableLiveData<Jugador> jugador =jugadoresViewModel.seleccionado();
        jugadoresViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Jugador>() {
            @Override
            public void onChanged(Jugador elemento) {
                //Glide.with().load(elemento.imagen).into(binding.imagen);
                binding.nombreJugador.setText(elemento.nombre);
                binding.dorsalJugador.setText(elemento.dorsal);
            }
        });


        //ComeBack
        binding.botonComeBackSeeTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

    }
}