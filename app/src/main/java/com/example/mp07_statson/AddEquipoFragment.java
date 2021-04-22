package com.example.mp07_statson;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentAddEquipoBinding;

public class AddEquipoFragment extends BaseFragment {

    private FragmentAddEquipoBinding binding;
    private EquipoViewModel equipoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddEquipoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(view1 -> {
            //para volver atras
            nav.popBackStack();
        });

        //para abrir la galeria i seleccionar foto
        binding.imagenEquipo.setOnClickListener(v->{
            lanzadorGaleria.launch("image/*");
        });

        //crearjugador
        binding.botonCrearAddEquipo.setOnClickListener(v->{
            String nombre = binding.nombreEquipo.getText().toString();

            String imagen = "file:///android_asset/equipo.png";
            if(equipoViewModel.imagenSeleccionada != null){
                imagen = equipoViewModel.imagenSeleccionada.toString();
                equipoViewModel.imagenSeleccionada = null;
            }

            //almacena la imagen
//            FirebaseStorage.getInstance().getReference().putFile(equipoViewModel.imagenSeleccionada);

            //guarda el equipo
            Equipo equipo = new Equipo(nombre, imagen);
            db.collection("equipos").add(equipo)
                    .addOnSuccessListener(documentReference -> {
                        String id = documentReference.getId();
                        db.collection("usuarios").document(auth.getUid()).collection("equipos").document(id).set(equipo);

//                            FirebaseFirestore.getInstance().collection("jugdores").add();
                    });

            //para volver atras
            nav.popBackStack();
        });

        if (equipoViewModel.imagenSeleccionada != null){
            //En caso de perdida, insertamos la imagen:
            Glide.with(this).load(equipoViewModel.imagenSeleccionada).into(binding.imagenEquipo);
        }
    }



    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                //albumsViewModel.establecerImagenSeleccionada(uri);
                equipoViewModel.imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.imagenEquipo);
            });
}