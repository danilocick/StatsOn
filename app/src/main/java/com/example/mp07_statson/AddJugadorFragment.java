package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Ppp;
import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddJugadorFragment extends BaseFragment {

    private FragmentAddJugadorBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        binding.imagenJugador.setOnClickListener(v-> lanzadorGaleria.launch("image/*"));

        //crearjugador
        binding.botonCrearAddJugador.setOnClickListener(v->{
            String nombre = binding.nombreJugador.getText().toString();
            String dorsalString = binding.dorsalJugador.getText().toString();
            int dorsal = Integer.parseInt(dorsalString);

            String imagen = "file:///android_asset/jugador.png";
            if(viewmodel.imagenJugadorSeleccionada != null){
                imagen = viewmodel.imagenJugadorSeleccionada.toString();
                viewmodel.imagenJugadorSeleccionada = null;
            }

            Jugador jugador = new Jugador(nombre, dorsal ,imagen);
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).orderBy("dorsal").addSnapshotListener((value, error) -> {
                List<Jugador> jugadors = new ArrayList<>();
                for(DocumentSnapshot documentSnapshot: value){
                    jugadors.add(documentSnapshot.toObject(Jugador.class));
                }
                boolean exist = false;
                for (int i = 0; i <jugadors.size(); i++) {
                    if (jugador.dorsal == jugadors.get(i).dorsal){
                        exist = true;
                    }
                }
                if (!exist){
                    db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).add(jugador).addOnSuccessListener(documentReference -> {
                        String idJugador = documentReference.getId();
                        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).document(idJugador).update("idJugador",idJugador);
                        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).document(idJugador).collection(FirebaseVar.PPP).add(new Ppp(0));
                    });
                    nav.popBackStack();
                }else {
                    Toast.makeText(requireActivity().getApplicationContext(), "El dorsal ya existe", Toast.LENGTH_LONG).show();
                }
            });
        });

        if (viewmodel.imagenJugadorSeleccionada != null){ Glide.with(this).load(viewmodel.imagenJugadorSeleccionada).into(binding.imagenJugador);}
    }

    ActivityResultLauncher<String> lanzadorGaleria = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        Glide.with(requireView()).load(uri).into(binding.imagenJugador);
        stor.getReference("imagenes/"+ UUID.randomUUID())
                .putFile(uri)
                .continueWithTask(task -> task.getResult().getStorage().getDownloadUrl())
                .addOnSuccessListener(url -> {
                    viewmodel.imagenJugadorSeleccionada = url;

                });

    });
}