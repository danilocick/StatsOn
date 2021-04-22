package com.example.mp07_statson;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoRivalBinding;
import com.example.mp07_statson.databinding.ViewholderEquipoBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class EquipoRivalFragment extends Fragment {

    private NavController navController;
    private FragmentEquipoRivalBinding binding;
    private EquipoViewModel equipoViewModel;
    boolean seleccionado = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Equipo> equipos = new ArrayList<>();
    EquiposbdAdapter equiposbdAdapter = new EquiposbdAdapter();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        equipos.clear();
        return (binding = FragmentEquipoRivalBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //insertar nombre rival
        binding.botonSiguienteERival.setOnClickListener(v -> {
//            String teamb = binding.nombreRival.getText().toString();
//
//            if(!teamb.equals("")) {
//                //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
//                equipoViewModel.insertar(teamb);
//                //navegamos
                navController.navigate(R.id.action_rivalFragment_to_equipoAyBFragment);
//            }
//            else {
//                // If name is not entered
//                Toast.makeText(requireActivity().getApplicationContext(), "Name is required", Toast.LENGTH_LONG).show();
//            }
        });


        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        db.collection("equipos").get().addOnSuccessListener(queryDocumentSnapshots -> {
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
                    //TODO: EQUIPO
//                    int color = Color.TRANSPARENT;
//                    Drawable background = holder.binding.background.getBackground();
//                    if (background instanceof ColorDrawable)
//                        color = ((ColorDrawable) background).getColor();

                  //holder.binding.background.setBackgroundColor(Color.rgb(218,165,32));
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