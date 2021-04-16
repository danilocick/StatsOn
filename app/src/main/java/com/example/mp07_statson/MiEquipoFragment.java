package com.example.mp07_statson;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

    private FirestoreRecyclerAdapter<Jugador, JugadorViewHolder> adapter;

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
//        JugadoresbdAdapter jugadoresbdAdapter = new JugadoresbdAdapter();
//        binding.listaJugadores.setAdapter(jugadoresbdAdapter);

        //printar jugadores
        //TODO: int m que se coja bien el numero, sin errores.
//        jugadoresViewModel.obtenerJugadoresDeEquipo(4).observe(getViewLifecycleOwner(), jugadoresbdAdapter::establecerJugadorList);





        Query query = db.collection("jugador");

        FirestoreRecyclerOptions<Jugador> options = new FirestoreRecyclerOptions.Builder<Jugador>()
                .setQuery(query, Jugador.class)
                .build();



        adapter = new FirestoreRecyclerAdapter<Jugador, JugadorViewHolder>(options) {
            @Override
            protected void onBindViewHolder(JugadorViewHolder jugadorViewHolder, int i, Jugador jugador) {
                jugadorViewHolder.binding.nombreJugador.setText(jugador.nombre);
            }


            @NonNull
            @Override
            public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new JugadorViewHolder(ViewholderJugadorEquipoABinding.inflate(getLayoutInflater()),parent,false);
            }
        };
        binding.listaJugadores.setAdapter(adapter);

    }



    //clase para acceder a los campos de viewholder_jugador_miteam
    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public class asd{
    }

}