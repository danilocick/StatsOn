package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.databinding.FragmentMenuBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MenuFragment extends BaseFragment {

    private FragmentMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentMenuBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonPartidoNuevo.setOnClickListener(view14 -> nav.navigate(R.id.action_menuFragment_to_rivalFragment));

        binding.botonResultados.setOnClickListener(view13 -> nav.navigate(R.id.action_menuFragment_to_listaPartidosFragment));

        binding.botonMiEquipo.setOnClickListener(view13 ->{
            createSantaColoma();
            viewmodel.idEquipoSeleccionado = FirebaseVar.ID_SANTACOLOMA;
            nav.navigate(R.id.action_menuFragment_to_resultadoMenuFragment);
        });

        binding.botonRivales.setOnClickListener(view12 -> nav.navigate(R.id.action_menuFragment_to_rivalesFragment));

        binding.botonOpciones.setOnClickListener(view1 -> nav.navigate(R.id.action_menuFragment_to_optionsFragment));
        
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).orderBy(FirebaseVar.nombreEquipo).addSnapshotListener((value, error) -> {
            ArrayList<Equipo> equipos = new ArrayList<>();
            for(DocumentSnapshot documentSnapshot: value){
                documentSnapshot.getId();
                Equipo equipo = documentSnapshot.toObject(Equipo.class);
                equipo.idEquipo = documentSnapshot.getId();
                equipos.add(equipo);
            }
        });

    }

    private void createSantaColoma() {
        String imagen = "file:///android_asset/santacoloma.png";
        Equipo equipo = new Equipo("Santa Coloma",imagen);
        db.collection(FirebaseVar.EQUIPOS).document(FirebaseVar.ID_SANTACOLOMA).set(equipo).addOnSuccessListener(documentReference -> {
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(FirebaseVar.ID_SANTACOLOMA).set(equipo);


            db.collection(FirebaseVar.EQUIPOS).document(FirebaseVar.ID_SANTACOLOMA).update("idEquipo", FirebaseVar.ID_SANTACOLOMA);
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(FirebaseVar.ID_SANTACOLOMA).update("idEquipo", FirebaseVar.ID_SANTACOLOMA);
        });
    }

}