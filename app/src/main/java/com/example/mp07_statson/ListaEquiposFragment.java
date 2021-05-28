package com.example.mp07_statson;

import android.app.AlertDialog;
import android.graphics.Rect;
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
import com.example.mp07_statson.databinding.FragmentRivalesBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;

public class ListaEquiposFragment extends BaseFragment {

    private FragmentRivalesBinding binding;
    EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();
    private ExplosionField mExplosionField;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRivalesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExplosionField = ExplosionField.attach2Window(requireActivity());


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

        binding.listaEquipos.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent != null && view != null) {
                    int itemPosition = parent.getChildAdapterPosition(view);
                    int totalCount = parent.getAdapter().getItemCount();

                    if (itemPosition >= 0) {
                        outRect.bottom = 15;
                        outRect.right = 15;
                    }
                }
            }
        });
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
                createDialog(equipo, v);
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

    private void createDialog(Equipo equipo, View v) {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(requireActivity());
        alertDlg.setMessage("¿Estás seguro que deseas eliminar al equipo?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Si", (dialog, which) ->{
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(equipo.idEquipo).delete();
            mExplosionField.explode(v);
        });

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