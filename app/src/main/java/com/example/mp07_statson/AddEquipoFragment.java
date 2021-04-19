package com.example.mp07_statson;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentAddEquipoBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class AddEquipoFragment extends Fragment {

    private NavController navController;
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
        navController = Navigation.findNavController(view);
        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
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

            //guarda el equipo
            FirebaseFirestore.getInstance().collection("equipos").add(new Equipo(nombre, imagen))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String s = documentReference.getId();
//                            FirebaseFirestore.getInstance().collection("jugdores").add();
                        }
                    });
            //para volver atras
            navController.popBackStack();
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