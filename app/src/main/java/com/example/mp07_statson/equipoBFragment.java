package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.databinding.FragmentEquipoB2Binding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class equipoBFragment extends BaseFragment {

    private FragmentEquipoB2Binding binding;
    JugadorAdapter jugadorAdapter = new JugadorAdapter();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentEquipoB2Binding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db.collection(FirebaseVar.JUGADORES).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Jugador> jugadors = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                jugadors.add(documentSnapshot.toObject(Jugador.class));
            }

            jugadorAdapter.establecerjugadores(jugadors);
        });
//        }

        binding.listaJugadoresTeamB.setAdapter(jugadorAdapter);
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

            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO:Something
                }
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

    //clase para acceder a los campos de viewholder_jugador_miteam
    static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}