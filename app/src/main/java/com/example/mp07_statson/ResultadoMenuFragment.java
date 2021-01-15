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
import com.example.mp07_statson.databinding.FragmentResultadoMenuBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;

import java.util.List;


public class ResultadoMenuFragment extends Fragment {

    private NavController navController;
    private FragmentResultadoMenuBinding binding;
    private JugadoresMiTMViewModel jugadoresViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentResultadoMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresMiTMViewModel.class);

        //ComeBack
        binding.botonComeBackMiTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //Ir anyadirjugador
        binding.botonanyadirjugadorMiTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_resultadoMenuFragment_to_addJugadorMTFragment);
            }
        });

        //obtener datos de los jugadores de la bd
        JugadoresbdAdapter jugadoresbdAdapter = new JugadoresbdAdapter();
        binding.listaJugadoresMiTM.setAdapter(jugadoresbdAdapter);
        jugadoresViewModel.obtener().observe(getViewLifecycleOwner(), jugadoresbdAdapter::establecerJugadorList);
    }

    //adaptador bd
    public class JugadoresbdAdapter extends RecyclerView.Adapter<JugadorViewHolder>{

        List<Jugador> jugadorList;

        @NonNull
        @Override
        public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JugadorViewHolder(ViewholderJugadorMiTeamBinding.inflate(getLayoutInflater()), parent, false);

        }

        @Override
        public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
            Jugador jugador = jugadorList.get(position);
            Glide.with(holder.itemView).load(jugador.imagen).into(holder.binding.imagenJugadorMiTeam);
            holder.binding.nombreJugador.setText(jugador.nombre);
            holder.binding.dorsalJugador.setText(jugador.dorsal);

            holder.binding.eliminarJugador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
                    jugadoresViewModel.delete(jugador);
                }
            });

            holder.binding.nombreJugador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jugadoresViewModel.seleccionar(jugador);
                    navController.navigate(R.id.action_resultadoMenuFragment_to_jugadorStatsFragment);
                }
            });

        }

        @Override
        public int getItemCount() {

            return jugadorList == null ? 0 : jugadorList.size();
        }

        public void establecerJugadorList(List<Jugador> jugadorList){
            this.jugadorList=jugadorList;
            notifyDataSetChanged();
        }

        public Jugador obtenerJugador (int posicion){
            return jugadorList.get(posicion);
        }

    }


    //clase para acceder a los campos de viewholder_jugador_miteam
    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorMiTeamBinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorMiTeamBinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}