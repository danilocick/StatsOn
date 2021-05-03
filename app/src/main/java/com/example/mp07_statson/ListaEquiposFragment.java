package com.example.mp07_statson;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.databinding.FragmentRivalesBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListaEquiposFragment extends BaseFragment {

    private FragmentRivalesBinding binding;
    EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRivalesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view12 -> nav.popBackStack());

        binding.botonanyadirequipo.setOnClickListener(view1 -> nav.navigate(R.id.action_listaEquiposFragment_to_addEquipoFragment));

        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS)
                .orderBy(FirebaseVar.nombreEquipo)
                .addSnapshotListener((value, error) -> {
            ArrayList<Equipo> equipos = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot: value){
                documentSnapshot.getId();
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                equipo.idEquipo = documentSnapshot.getId();
                if (!equipo.idEquipo.equals(FirebaseVar.ID_SANTACOLOMA)) {
                    equipos.add(equipo);
                }
            }
            equiposbdAdapter.establecerEquipoList(equipos);
        });

        binding.listaEquipos.setAdapter(equiposbdAdapter);
    }

    public class EquiposbdAdapter extends RecyclerView.Adapter<ListaEquiposFragment.EquipoViewHolder>{
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
                holder.binding.background.setOnClickListener(view -> {
                    viewmodel.idEquipoSeleccionado = equipo.idEquipo;
                    viewmodel.nombreEquipoSeleccionado = equipo.nombreEquipo;
                    nav.navigate(R.id.action_listaEquiposFragment_to_miEquipoFragment);
                });

            holder.binding.background.setOnLongClickListener(v -> {
                createDialog(equipo);
                return false;
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

    private void createDialog(Equipo equipo) {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(requireActivity());
        alertDlg.setMessage("Are you sure you want to delete?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Yes", (dialog, which) -> db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(equipo.idEquipo).delete());

        alertDlg.setNegativeButton("No", (dialog, which) -> {});

        alertDlg.show();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder{
        ViewholderEquipoBinding binding;
        public EquipoViewHolder(@NonNull ViewholderEquipoBinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}