package com.example.mp07_statson;

import android.media.audiofx.DynamicsProcessing;
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
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentRivalesBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;

import java.util.List;

public class RivalesFragment extends Fragment {

    private NavController navController;
    private FragmentRivalesBinding binding;
    private EquipoViewModel equipoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentRivalesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //Ir anyadirjugador

        //obtener datos de los jugadores de la bd
        EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();
        binding.listaEquipos.setAdapter(equiposbdAdapter);

        //printar jugadores
        //TODO: int m que se coja bien el numero, sin errores.
//        equipoViewModel.obtener().observe(getViewLifecycleOwner(), equiposbdAdapter::establecerEquipoList);
    }

    //adaptador bd
    public class EquiposbdAdapter extends RecyclerView.Adapter<RivalesFragment.EquipoViewHolder>{

        List<Equipo> equipoList;

        @NonNull
        @Override
        public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EquipoViewHolder(ViewholderEquipoBinding.inflate(getLayoutInflater()), parent, false);
        }

        @Override
        public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
            Equipo equipo = equipoList.get(position);
            //Glide.with(holder.itemView).load(equipo.imagen).into(holder.binding.imagenEquipo);
            holder.binding.nombreEquipo.setText(equipo.toString());
            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: EQUIPO
                    equipoViewModel.seleccionar(equipo);
                    navController.navigate(R.id.action_rivalesFragment_to_resultadoMenuFragment);
                }
            });

        }

        @Override
        public int getItemCount() {

            return equipoList == null ? 0 : equipoList.size();
        }

        public void establecerEquipoList(List<Equipo> equipoList){
            this.equipoList =equipoList;
            notifyDataSetChanged();
        }

        public Equipo obtenerEquipo(int posicion){
            return equipoList.get(posicion);
        }

    }


    //clase para acceder a los campos de viewholder_jugador_miteam
    public static class EquipoViewHolder extends RecyclerView.ViewHolder{
        ViewholderEquipoBinding binding;

        public EquipoViewHolder(@NonNull ViewholderEquipoBinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}