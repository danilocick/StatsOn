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
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.databinding.FragmentMiEquipoBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import tyrantgit.explosionfield.ExplosionField;


public class MiEquipoFragment extends BaseFragment {

    private FragmentMiEquipoBinding binding;
    private ExplosionField mExplosionField;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiEquipoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mExplosionField = ExplosionField.attach2Window(requireActivity());

        binding.botonComeBack.setOnClickListener(v -> nav.popBackStack());

        binding.botonanyadirjugador.setOnClickListener(v -> nav.navigate(R.id.action_miEquipoFragment_to_addJugadorFragment));

        binding.miEquipo.setText(viewmodel.nombreEquipoSeleccionado);

        JugadorAdapter jugadorAdapter = new JugadorAdapter();
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES)
                .orderBy(FirebaseVar.DORSAL)
                .addSnapshotListener((value, error) -> {
            List<Jugador> jugadors = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot: value){
                jugadors.add(documentSnapshot.toObject(Jugador.class));
            }
            jugadorAdapter.establecerjugadores(jugadors);
        });

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
                viewmodel.jugadorSeleccionado = jugador;
                nav.navigate(R.id.action_miEquipoFragment_to_jugadorStatsFragment);
            });
            holder.binding.background.setOnLongClickListener(v -> {
                createDialog(jugador,v);
                return false;
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

    private void createDialog(Jugador jugador, View v) {
        AlertDialog.Builder alertDlg = new AlertDialog.Builder(requireActivity());
        alertDlg.setMessage("¿Estás seguro que deseas eliminar al jugador?");
        alertDlg.setCancelable(false);

        alertDlg.setPositiveButton("Si", (dialog, which) -> {
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).document(jugador.idJugador).delete();
            mExplosionField.explode(v);
        });

        alertDlg.setNegativeButton("No", (dialog, which) -> {});

        alertDlg.show();
    }

    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}