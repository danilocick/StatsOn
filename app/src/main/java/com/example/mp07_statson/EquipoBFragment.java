package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresTeamBViewModel;
import com.example.mp07_statson.ViewModel.NombreRivalViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoBBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;

import java.util.List;


public class EquipoBFragment extends Fragment {

    private NavController navController;
    private FragmentEquipoBBinding binding;
    private JugadoresTeamBViewModel jugadorsTeamBViewModel;

    private NombreRivalViewModel nombreRivalViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEquipoBBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        /* TODO:SetText from jugadoresMiTMViewModel.getNombre()
        binding.tituloTeamB.setText(String.format("%s", nombreRival.getNombreRival()));
        */

        nombreRivalViewModel = new ViewModelProvider(requireActivity()).get(NombreRivalViewModel.class);

        nombreRivalViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.nombreRival.setText(a));

        //empezar partido
        binding.botonIniciarPartidoTeamB.setOnClickListener(view1 -> navController.navigate(R.id.action_equipoBFragment_to_gameFragment));

        //ComeBack
        binding.botonComeBackTeamB.setOnClickListener(view12 -> {
            //para volver atras
            navController.popBackStack();
        });

        jugadorsTeamBViewModel = new ViewModelProvider(requireActivity()).get(JugadoresTeamBViewModel.class);

        //Ir anyadirjugador
        binding.botonanyadirjugador.setOnClickListener(view13 -> navController.navigate(R.id.action_equipoBFragment_to_addJugadorFragment));

        JugadoresbdTeamBAdapter adapter = new JugadoresbdTeamBAdapter();
        binding.listaJugadoresTeamB.setAdapter(adapter);

        jugadorsTeamBViewModel.obtener().observe(getViewLifecycleOwner(), adapter::establecerJugadoresList);

    }

    //clase para acceder a los campos de viewholder_jugador_miteam
    static class JugadorTeamBViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorMiTeamBinding binding;

        public JugadorTeamBViewHolder(@NonNull ViewholderJugadorMiTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //adaptador bd
    class JugadoresbdTeamBAdapter extends RecyclerView.Adapter<EquipoBFragment.JugadorTeamBViewHolder>{

        List<Jugador> jugadorList;

        @NonNull
        @Override
        public JugadorTeamBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JugadorTeamBViewHolder(ViewholderJugadorMiTeamBinding.inflate(getLayoutInflater(),parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull JugadorTeamBViewHolder holder, int position) {
                Jugador jugador = jugadorList.get(position);
                Glide.with(holder.itemView).load(jugador.imagen).into(holder.binding.imagenJugadorMiTeam);
                holder.binding.nombreJugador.setText(jugador.nombre);
                holder.binding.dorsalJugador.setText(jugador.dorsal);

            holder.binding.eliminarJugador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
                    jugadorsTeamBViewModel.delete(jugador);
                }
            });
        }

        @Override
        public int getItemCount() {
            return jugadorList == null ? 0 : jugadorList.size();
        }

        void establecerJugadoresList(List<Jugador> jugadorList){
            this.jugadorList = jugadorList;
            notifyDataSetChanged();
        }
    }

}