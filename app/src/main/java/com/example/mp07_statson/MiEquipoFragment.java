package com.example.mp07_statson;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentMiEquipoBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static android.content.ContentValues.TAG;


public class MiEquipoFragment extends Fragment {

    private NavController navController;
    private FragmentMiEquipoBinding binding;
    private JugadoresViewModel jugadoresViewModel;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMiEquipoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //Ir anyadirjugador
        binding.botonanyadirjugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_resultadoMenuFragment_to_addJugadorFragment);
            }
        });

        //obtener datos de los jugadores de la bd
        JugadoresbdAdapter jugadoresbdAdapter = new JugadoresbdAdapter();
        binding.listaJugadores.setAdapter(jugadoresbdAdapter);


        db.collection("jugadores")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        FirestoreRecyclerOptions<Jugador> options = new FirestoreRecyclerOptions.Builder<Jugador>()
                .setQuery(query, ProductModel.class)
                .build();

        db.collection("jugadores");

        //printar jugadores
        //TODO: int m que se coja bien el numero, sin errores.
        int m = 4;
//        jugadoresViewModel.obtenerJugadoresDeEquipo(m).observe(getViewLifecycleOwner(), jugadoresbdAdapter::establecerJugadorList);
    }

    //adaptador bd
    public class JugadoresbdAdapter extends RecyclerView.Adapter<JugadorViewHolder>{

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

//            holder.binding.eliminarJugador.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
//                    jugadoresViewModel.delete(jugador);
//                }
//            });

            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jugadoresViewModel.seleccionar(jugador);
                    navController.navigate(R.id.action_resultadoMenuFragment_to_jugadorStatsFragment);
                }
            });

        }

        @Override
        public int getItemCount() {

            return jugadorList == null ? 0 : jugadorList.size();
        }

        public void establecerJugadorList(List<Jugador> jugadorList){
            this.jugadorList=jugadorList;
            notifyDataSetChanged();
        }

        public Jugador obtenerJugador (int posicion){
            return jugadorList.get(posicion);
        }

    }


    //clase para acceder a los campos de viewholder_jugador_miteam
    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}