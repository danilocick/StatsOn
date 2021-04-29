package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.databinding.FragmentAddEquipoBinding;

import java.util.UUID;

public class AddEquipoFragment extends BaseFragment {

    private FragmentAddEquipoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddEquipoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view1 -> { nav.popBackStack();});

        binding.imagenEquipo.setOnClickListener(v -> { lanzadorGaleria.launch("image/*"); });

        binding.botonCrearAddEquipo.setOnClickListener(v -> {

            String nombre = binding.nombreEquipo.getText().toString();
            String imagen = "file:///android_asset/equipo.png";
            if (viewmodel.imagenEquipoSeleccionada != null) {
                imagen = viewmodel.imagenEquipoSeleccionada.toString();
                viewmodel.imagenEquipoSeleccionada = null;
            }

            //guarda el equipo
            Equipo equipo = new Equipo(nombre, imagen);
            db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).add(equipo).addOnSuccessListener(documentReference -> {
                String id = documentReference.getId();
                db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(id).update("idEquipo",id);
            });

            nav.popBackStack();
        });

        if (viewmodel.imagenEquipoSeleccionada != null) { Glide.with(this).load(viewmodel.imagenEquipoSeleccionada).into(binding.imagenEquipo); }
    }


    ActivityResultLauncher<String> lanzadorGaleria = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
        Glide.with(requireView()).load(uri).into(binding.imagenEquipo);
        stor.getReference("imagenes/"+ UUID.randomUUID())
                .putFile(uri)
                .continueWithTask(task -> task.getResult().getStorage().getDownloadUrl())
                .addOnSuccessListener(url -> {
                    viewmodel.imagenEquipoSeleccionada = url;

                });

    });
}