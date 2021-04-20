package com.example.mp07_statson;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentMiEquipoBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MiEquipoFragment extends Fragment {

    private NavController navController;
    private FragmentMiEquipoBinding binding;
    private JugadoresViewModel jugadoresViewModel;

    private DatabaseReference mDatabase;
    List<Jugador> jugadorList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMiEquipoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        mDatabase = FirebaseDatabase.getInstance().getReference();

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


        JugadoresAdapter jugadoresAdapter = new JugadoresAdapter();

        mDatabase.child("jugadores").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });



        jugadoresAdapter.establecerLista(jugadorList);
        binding.listaJugadores.setAdapter(jugadoresAdapter);
    }



    class JugadoresAdapter extends RecyclerView.Adapter<JugadorViewHolder> {

        List<Jugador> jugadors;

        @NonNull
        @Override
        public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JugadorViewHolder(ViewholderJugadorEquipoABinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {

            Jugador jugador = jugadors.get(position);

            holder.binding.nombreJugador.setText(jugador.nombre);
            holder.binding.dorsalJugador.setText(jugador.dorsal);
        }

        @Override
        public int getItemCount() {
            return jugadors != null ? jugadors.size() : 0;
        }

        public void establecerLista(List<Jugador> jugadors){
            this.jugadors = jugadors;
            notifyDataSetChanged();
        }
    }

    //clase para acceder a los campos de viewholder_jugador_miteam
    public static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}