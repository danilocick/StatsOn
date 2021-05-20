package com.example.mp07_statson;

import android.app.AlertDialog;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.PartidoViewModel;
import com.example.mp07_statson.ViewModel.StatsOnViewModel;
import com.example.mp07_statson.databinding.FragmentCambioBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

public class CambioFragment extends DialogFragment {

    private FragmentCambioBinding binding;
    public NavController nav;
    public FirebaseAuth auth;
    public FirebaseFirestore db;
    public StatsOnViewModel viewmodel;
    public PartidoViewModel partidoviewmodel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCambioBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel = new ViewModelProvider(requireActivity()).get(StatsOnViewModel.class);
        partidoviewmodel = new ViewModelProvider(requireActivity()).get(PartidoViewModel.class);
        List<Jugador> listJ = new ArrayList<>();
        JugadorAdapter jugadorAdapter = new JugadorAdapter();

        if (partidoviewmodel.seleccionEquipo){
            for(Jugador j : partidoviewmodel.jugadoresEquipoLocal){
                if (j.starter) {

                }else {
                    listJ.add(j);
                }
            }
            jugadorAdapter.establecerjugadores(listJ);
        }else {
            for(Jugador j : partidoviewmodel.jugadoresEquipoVisitante){
                if (j.starter) {

                }else {
                    listJ.add(j);
                }
            }
            jugadorAdapter.establecerjugadores(listJ);

        }


        binding.listaJugadores.setAdapter(jugadorAdapter);

        binding.listaJugadores.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent != null && view != null) {

                    int itemPosition = parent.getChildAdapterPosition(view);
                    int totalCount = parent.getAdapter().getItemCount();

                    if (itemPosition >= 0) {
                        outRect.bottom = 24;
                        outRect.right = 24;
                    }
                }
            }
        });
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
            holder.binding.nombreJugador.setText(jugador.nombre);
            holder.binding.dorsalJugador.setText(String.valueOf(jugador.dorsal));

            holder.binding.background.setOnClickListener(v->{
//                viewmodel.jugadorSeleccionado = jugador;
                viewmodel.jugadorSeleccionado.starter = true;
                dismiss();
            });

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


    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}