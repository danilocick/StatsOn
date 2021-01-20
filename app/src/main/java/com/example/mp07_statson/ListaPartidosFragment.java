package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mp07_statson.Model.Partido;
import com.example.mp07_statson.ViewModel.PartidosViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoBBinding;
import com.example.mp07_statson.databinding.FragmentListaPartidosBinding;
import com.example.mp07_statson.databinding.ViewholderPartidoBinding;

import java.util.List;


public class ListaPartidosFragment extends Fragment {

    private NavController navController;
    private FragmentListaPartidosBinding binding;
    private PartidosViewModel partidosViewModel;


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

        PartidoAdapter partidoAdapter = new PartidoAdapter();
        binding.listaPartidos.setAdapter(partidoAdapter);
        partidosViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Partido>>() {
            @Override
            public void onChanged(List<Partido> elementos) {
                partidoAdapter.establecerLista(elementos);
            }
        });

        //ComeBack
        binding.botonComeBackPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();

            }
        });

    }

    //accederPartidos
    class PartidoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderPartidoBinding binding;

        public PartidoViewHolder(ViewholderPartidoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class PartidoAdapter extends RecyclerView.Adapter<PartidoViewHolder> {

        List<Partido> partidos;

        @NonNull
        @Override
        public PartidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new PartidoViewHolder(ViewholderPartidoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull PartidoViewHolder holder, int position) {

            Partido elemento = partidos.get(position);

            holder.binding.local.setText(elemento.nombreLocal);
            holder.binding.visitante.setText(elemento.nombreVisitante);

            holder.binding.local.setOnClickListener(view -> navController.navigate(R.id.action_listaPartidosFragment_to_outputMatchesFragment));
            holder.binding.visitante.setOnClickListener(view -> navController.navigate(R.id.action_listaPartidosFragment_to_outputMatchesFragment));
            holder.binding.vs.setOnClickListener(view -> navController.navigate(R.id.action_listaPartidosFragment_to_outputMatchesFragment));
        }

        @Override
        public int getItemCount() {
            return partidos != null ? partidos.size() : 0;
        }

        public void establecerLista(List<Partido> partidos){
            this.partidos = partidos;
            notifyDataSetChanged();
        }
    }


}