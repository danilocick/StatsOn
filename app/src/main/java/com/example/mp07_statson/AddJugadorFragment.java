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
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddJugadorFragment extends Fragment {

    private NavController navController;
    private FragmentAddJugadorBinding binding;
    private JugadoresViewModel jugadoresViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorBinding.inflate(inflater, container, false)).getRoot();
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

            int idEquipo = 4;
            //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
//            jugadoresViewModel.insertar(nombre, dorsal, imagen, idEquipo);

//            binding.botonCrearAddJugador.

            FirebaseFirestore.getInstance().collection("jugdores").add(new Jugador(nombre,dorsal, imagen, 2))
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            String s = documentReference.getId();
                            FirebaseFirestore.getInstance().collection("jugdores").add();
                        }
                    })
            ;

            //para volver atras
            navController.popBackStack();
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