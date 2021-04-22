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
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentRivalesBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RivalesFragment extends BaseFragment {

    private FragmentRivalesBinding binding;
    private EquipoViewModel equipoViewModel;

    ArrayList<Equipo> equipos = new ArrayList<>();
    EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        equipos.clear();
        return (binding = FragmentRivalesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(view12 -> {
            //para volver atras
            nav.popBackStack();
        });

        //Ir anyadirjugador
        binding.botonanyadirequipo.setOnClickListener(view1 -> {
            //para volver atras
            nav.navigate(R.id.action_rivalesFragment_to_addEquipoFragment);
        });

        db.collection("usuarios").document(auth.getUid()).collection("equipos").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                equipos.clear();
                for(DocumentSnapshot documentSnapshot: value){
                    equipos.add(documentSnapshot.toObject(Equipo.class));
                }
                equiposbdAdapter.establecerEquipoList(equipos);
            }
        });

///*
//        db.collection("usuarios").document(auth.getUid()).collection("equipos").get().addOnSuccessListener(queryDocumentSnapshots -> {
//            for(DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
//                equipos.add(documentSnapshot.toObject(Equipo.class));
//            }
//            equiposbdAdapter.establecerEquipoList(equipos);
//        });
//*/

        //obtener datos de los jugadores de la bd
        binding.listaEquipos.setAdapter(equiposbdAdapter);
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
            Glide.with(holder.itemView).load(equipo.imagen).into(holder.binding.imagenEquipo);
            holder.binding.nombreEquipo.setText(equipo.nombreEquipo);
            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: EQUIPO
                    equipoViewModel.seleccionar(equipo);
                    equipoViewModel.setRival(true);
                    nav.navigate(R.id.action_rivalesFragment_to_resultadoMenuFragment);
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