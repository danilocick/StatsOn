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
import com.example.mp07_statson.databinding.FragmentAddJugadorMTBinding;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;


public class AddJugadorMTFragment extends Fragment {

    private NavController navController;
    private FragmentAddJugadorMTBinding binding;

    Uri imagenSeleccionada;
    private JugadoresMiTMViewModel jugadoresMiTMViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentAddJugadorMTBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        jugadoresMiTMViewModel = new ViewModelProvider(requireActivity()).get(JugadoresMiTMViewModel.class);

        //ComeBack
        binding.botonComeBackAddJTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorMTFragment_to_resultadoMenuFragment);
            }
        });

        //Crear Jugador
        binding.botonCrearAddJTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addJugadorMTFragment_to_resultadoMenuFragment);
            }
        });

        //insertar imagen
        binding.imagenJugadorMiTeam.setOnClickListener(v -> {
            abrirGaleria();
        });

        //insertar jugador
        binding.botonCrearAddJTM.setOnClickListener(v -> {
            String nombre = binding.nombreJugador.getText().toString();
            String dorsal = binding.dorsalJugador.getText().toString();

            //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
            jugadoresMiTMViewModel.insertar(nombre, dorsal, imagenSeleccionada);

        });
    }

    private void abrirGaleria(){
        //comprobar si el usuari ha dado permiso
        if (checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            lanzadorGaleria.launch("image/*");
        } else {
            //lanza el dialogo
            lanzadorPermisos.launch(READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                //albumsViewModel.establecerImagenSeleccionada(uri);

                //guardar la imagen seleccionada para pasarla
                imagenSeleccionada = uri;
                //nos muestra la miniatura cargada
                Glide.with(requireView()).load(uri).into(binding.imagenJugadorMiTeam);
            });

    private final ActivityResultLauncher<String> lanzadorPermisos =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    lanzadorGaleria.launch("image/*");
                }
            });
}