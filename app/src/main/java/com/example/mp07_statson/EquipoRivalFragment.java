package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.databinding.FragmentEquipoRivalBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class EquipoRivalFragment extends BaseFragment {

    private FragmentEquipoRivalBinding binding;
    boolean seleccionado = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentEquipoRivalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //insertar nombre rival
        binding.botonSiguienteERival.setOnClickListener(v -> {
                nav.navigate(R.id.action_equipoRivalFragment_to_equipoAyBFragment);
        });


        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                nav.popBackStack();
            }
        });

        EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();
        db.collection("usuarios").document(auth.getUid()).collection("equipos").get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Equipo> equipos = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                equipos.add(documentSnapshot.toObject(Equipo.class));
            }

            equiposbdAdapter.establecerEquipoList(equipos);
        });

        binding.listaEquipos.setAdapter(equiposbdAdapter);
     }


    public class EquiposbdAdapter extends RecyclerView.Adapter<EquipoViewHolder>{

        List<Equipo> equipoList;

        @NonNull
        @Override
        public EquipoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EquipoViewHolder(ViewholderEquipoBinding.inflate(getLayoutInflater()), parent, false);
        }

        @Override
        public void onBindViewHolder(@NonNull EquipoViewHolder holder, int position) {
            Equipo equipo = equipoList.get(position);
            Glide.with(holder.itemView).load(equipo.imagen).into(holder.binding.imagenEquipo);
            holder.binding.nombreEquipo.setText(equipo.nombreEquipo);
            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!seleccionado){
                        holder.binding.background.setBackgroundColor(Color.rgb(218,165,32));
                        seleccionado = true;
                    }else {
                            holder.binding.background.setBackgroundColor(Color.rgb(255, 255, 255));
                            seleccionado = false;
                    }
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