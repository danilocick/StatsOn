package com.example.mp07_statson;

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
import com.example.mp07_statson.databinding.FragmentEquipoRivalBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class EquipoRivalFragment extends BaseFragment {

    private FragmentEquipoRivalBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentEquipoRivalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).orderBy(FirebaseVar.nombreEquipo).addSnapshotListener((value, error) -> {
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
            holder.binding.background.setOnClickListener(view -> {

                if (binding.iconSwitch.isChecked()){
                    viewmodel.idEquipoLocal = equipo.idEquipo;
                    viewmodel.nombreEquipoLocal = equipo.nombreEquipo;
                    viewmodel.imagenEquipoLocal = equipo.imagen;
                    viewmodel.idEquipoVisitante = FirebaseVar.ID_SANTACOLOMA;
                    viewmodel.nombreEquipoVisitante = "Santa Coloma";
                    viewmodel.imagenEquipoVisitante = "file:///android_asset/santacoloma.png";
                }else {
                    viewmodel.idEquipoLocal = FirebaseVar.ID_SANTACOLOMA;
                    viewmodel.nombreEquipoLocal = "Santa Coloma";
                    viewmodel.imagenEquipoLocal = "file:///android_asset/santacoloma.png";
                    viewmodel.idEquipoVisitante = equipo.idEquipo;
                    viewmodel.nombreEquipoVisitante = equipo.nombreEquipo;
                    viewmodel.imagenEquipoVisitante = equipo.imagen;
                }
                nav.navigate(R.id.action_equipoRivalFragment_to_equipoAyBFragment);
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