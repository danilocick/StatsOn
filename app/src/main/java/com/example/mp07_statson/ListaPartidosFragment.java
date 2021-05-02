package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.databinding.FragmentListaPartidosBinding;
import com.example.mp07_statson.databinding.ViewholderPartidoBinding;

import java.util.List;


public class ListaPartidosFragment extends BaseFragment {

    private FragmentListaPartidosBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentListaPartidosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PartidosbdAdapter partidosbdAdapter = new PartidosbdAdapter();
        binding.listaPartidos.setAdapter(partidosbdAdapter);

        binding.botonComeBackPartidos.setOnClickListener(view1 -> nav.popBackStack());
    }

    public class PartidosbdAdapter extends RecyclerView.Adapter<PartidoViewHolder>{
        List<Partido> partidosList;

        @NonNull
        @Override
        public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PartidoViewHolder(ViewholderPartidoBinding.inflate(getLayoutInflater()), parent, false);
        }

        @Override
        public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {
            Partido partido = partidosList.get(position);

            holder.binding.puntosLocal.setText(String.valueOf(partido.puntosLocal));
            holder.binding.nombreVisitante.setText(String.valueOf(partido.nombreEquipoVisitante));
            holder.binding.puntosVisitante.setText(String.valueOf(partido.puntosVisitante));

            holder.binding.recycler.setOnClickListener(view -> nav.navigate(R.id.action_listaPartidosFragment_to_outputMatchesFragment));
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

    static class PartidoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderPartidoBinding binding;

        public PartidoViewHolder(ViewholderPartidoBinding binding, ViewGroup parent, boolean b) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}