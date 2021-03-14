package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.ViewModel.PartidosViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoBBinding;
import com.example.mp07_statson.databinding.FragmentListaPartidosBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;
import com.example.mp07_statson.databinding.ViewholderPartidoBinding;

import java.util.List;


public class ListaPartidosFragment extends Fragment {

    private NavController navController;
    private FragmentListaPartidosBinding binding;
    private PartidosViewModel partidosViewModel;

    //PARA HACER PRUEBAS
    boolean bol= true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentListaPartidosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        partidosViewModel = new ViewModelProvider(requireActivity()).get(PartidosViewModel.class);

        //obtener datos de los partidos de la bd
        PartidosbdAdapter partidosbdAdapter = new PartidosbdAdapter();
        binding.listaPartidos.setAdapter(partidosbdAdapter);
        partidosViewModel.obtener().observe(getViewLifecycleOwner(), partidosbdAdapter::establecerPartidoList);


        //TODO: IF TABLE ON DATABASE IS EMPTY, LOAD DATA(TO SEE)
        if (bol){
            Partido p1 = new Partido(0, 1, 50,33);
            Partido p2 = new Partido(0, 2, 60,61);
            Partido p3 = new Partido(0, 3, 70,50);
            Partido p4 = new Partido(0, 4, 80,40);
            partidosViewModel.insertar(p1);
            partidosViewModel.insertar(p2);
            partidosViewModel.insertar(p3);
            partidosViewModel.insertar(p4);

            bol = false;
        }


        //ComeBack
        binding.botonComeBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });


    }

    //adaptador bd
    public class PartidosbdAdapter extends RecyclerView.Adapter<PartidoViewHolder>{

        List<Partido> partidosList;
        private EquipoViewModel equipoViewModel;

        @NonNull
        @Override
        public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PartidoViewHolder(ViewholderPartidoBinding.inflate(getLayoutInflater()), parent, false);

        }

        @Override
        public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {

            equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

            Partido partido = partidosList.get(position);

            //if( Equipo.idEquipo == partido.idLocal){
            //    holder.binding.nombreLocal.setText(Equipo.getNombre);
            //}

            holder.binding.nombreLocal.setText(String.valueOf(partido.idLocal));
            holder.binding.puntosLocal.setText(String.valueOf(partido.puntosLocal));
            holder.binding.nombreVisitante.setText(String.valueOf(partido.idVisitante));
            holder.binding.puntosVisitante.setText(String.valueOf(partido.puntosVisitante));

            holder.binding.recycler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    partidosViewModel.seleccionar(partido);
                    navController.navigate(R.id.action_listaPartidosFragment_to_outputMatchesFragment);
                }
            });
        }

        @Override
        public int getItemCount() {

            return partidosList == null ? 0 : partidosList.size();
        }

        public void establecerPartidoList(List<Partido> partidosList) {
            this.partidosList = partidosList;
            notifyDataSetChanged();
        }

        public Partido obtenerPartido (int posicion){
            return partidosList.get(posicion);
        }
    }

    //accederPartidos
    static class PartidoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderPartidoBinding binding;

        public PartidoViewHolder(ViewholderPartidoBinding binding, ViewGroup parent, boolean b) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}