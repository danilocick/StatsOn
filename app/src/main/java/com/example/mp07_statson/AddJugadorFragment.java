package com.example.mp07_statson;

import android.net.Uri;
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
import com.example.mp07_statson.ViewModel.JugadoresTeamBViewModel;
import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class AddJugadorFragment extends Fragment {

    private NavController navController;
    private FragmentAddJugadorBinding binding;
    Uri imagenSeleccionada;
    private JugadoresTeamBViewModel jugadoresTeamBViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        jugadoresTeamBViewModel = new ViewModelProvider(requireActivity()).get(JugadoresTeamBViewModel.class);

        //ComeBack
        binding.botonComeBackERival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //para abrir la galeria i seleccionar foto
        binding.imagenJugadorTeamB.setOnClickListener(v->{
            abrirGaleria();
        });

        //crearjugador
        binding.botonCrearAddJTM.setOnClickListener(v->{
            String nombre = binding.nombreJugador.getText().toString();
            String dorsal = binding.dorsalJugador.getText().toString();

            //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
            jugadoresTeamBViewModel.insertar(nombre, dorsal, imagenSeleccionada);

            //para volver atras
            navController.popBackStack();
        });
    }

    //abrirgaleria
    private void abrirGaleria(){
        if (checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            lanzadorGaleria.launch("image/*");
        } else {
            lanzadorPermisos.launch(READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                //albumsViewModel.establecerImagenSeleccionada(uri);
                imagenSeleccionada = uri;
                Glide.with(requireView()).load(uri).into(binding.imagenJugadorTeamB);
            });

    private final ActivityResultLauncher<String> lanzadorPermisos =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    lanzadorGaleria.launch("image/*");
                }
            });
}