package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresMiTMViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoABinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;


import java.util.List;


public class EquipoAFragment extends Fragment {

    private NavController navController;
    private FragmentEquipoABinding binding;
    private JugadoresMiTMViewModel jugadoresMiTMViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEquipoABinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresMiTMViewModel = new ViewModelProvider(requireActivity()).get(JugadoresMiTMViewModel.class);

        binding.botonSiguienteTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_equipoAFragment_to_equipoBFragment);
            }
        });

        binding.botonComeBackTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        JugadorAdapter jugadorAdapter = new JugadorAdapter();
        binding.listaJugadoresTeamA.setAdapter(jugadorAdapter);
        //acceder al viewModel
        jugadoresMiTMViewModel.obtener().observe(getViewLifecycleOwner(), jugadorAdapter::establecerjugadores);
    }

    class JugadorAdapter extends RecyclerView.Adapter<JugadorViewHolder>{

        List<Jugador> jugadorList;
        @NonNull
        @Override
        public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JugadorViewHolder(ViewholderJugadorEquipoABinding.inflate(getLayoutInflater()), parent, false);
        }

        @Override
        public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
            Jugador jugador = jugadorList.get(position);
            Glide.with(holder.itemView).load(jugador.imagen).into(holder.binding.imagenJugador);
            holder.binding.nombreJugadorEquipoA.setText(jugador.nombre);
            holder.binding.dorsalJugadorEquipoA.setText(jugador.dorsal);
        }

        @Override
        public int getItemCount() {
            return jugadorList == null ? 0 : jugadorList.size();
        }

        void establecerjugadores(List<Jugador> jugadorList){
            this.jugadorList = jugadorList;
            notifyDataSetChanged();
        }
    }

    //clase para acceder a los campos de viewholder_jugador_miteam
    static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}