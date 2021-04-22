package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Equipo;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddJugadorFragment extends BaseFragment {

    private FragmentAddJugadorBinding binding;
    private JugadoresViewModel jugadoresViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresViewModel.class);

        //ComeBack
        binding.botonComeBack.setOnClickListener(view1 -> {
            //para volver atras
            nav.popBackStack();
        });

        //para abrir la galeria i seleccionar foto
        binding.imagenJugador.setOnClickListener(v->{
            lanzadorGaleria.launch("image/*");
        });

        //crearjugador
        binding.botonCrearAddJugador.setOnClickListener(v->{
            String nombre = binding.nombreJugador.getText().toString();
            String dorsalString = binding.dorsalJugador.getText().toString();
            int dorsal = Integer.parseInt(dorsalString);

            String imagen = "file:///android_asset/jugador.png";
            if(jugadoresViewModel.imagenSeleccionada != null){
                imagen = jugadoresViewModel.imagenSeleccionada.toString();
                jugadoresViewModel.imagenSeleccionada = null;
            }

            Equipo equipo = new Equipo(nombre, imagen);
            db.collection("jugadores").add(equipo)
                    .addOnSuccessListener(documentReference -> {
                        String id = documentReference.getId();
                        db.collection("usuarios").document(auth.getUid()).collection("jugadores").document(id).set(equipo);
                    });

            //para volver atras
            nav.popBackStack();
        });

        if (jugadoresViewModel.imagenSeleccionada != null){
            //En caso de perdida, insertamos la imagen:
            Glide.with(this).load(jugadoresViewModel.imagenSeleccionada).into(binding.imagenJugador);
        }
    }

    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                //albumsViewModel.establecerImagenSeleccionada(uri);
                jugadoresViewModel.imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.imagenJugador);
            });
}