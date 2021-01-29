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
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentEditJugadorBinding;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class EditJugadorFragment extends Fragment {


    private FragmentEditJugadorBinding binding;
    private NavController navController;
    private JugadoresViewModel jugadoresViewModel;

    Uri imagenSeleccionada;
    private JugadoresViewModel jugadoresMiTMViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEditJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresViewModel.class);

        jugadoresViewModel.seleccionado().observe(getViewLifecycleOwner(), elemento -> {
            Glide.with(EditJugadorFragment.this).load(elemento.imagen).into(binding.imagenJugadorMiTeam);
            binding.nombreJugador.setText(elemento.nombre);
            binding.dorsalJugador.setText(String.valueOf(elemento.dorsal));
        });
        //ComeBack
        binding.botonComeBackERival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //insertar imagen
        binding.imagenJugadorMiTeam.setOnClickListener(v -> {
            abrirGaleria();
        });

        //insertar jugador
        binding.botonCrearAddJTM.setOnClickListener(v -> {
            String nombre = binding.nombreJugador.getText().toString();
            String dorsalString = binding.dorsalJugador.getText().toString();
            int dorsal = Integer.parseInt(dorsalString);
            int idEquipo = 0;

            Jugador jugador = new Jugador(nombre,dorsal,imagenSeleccionada.toString(), idEquipo);

            jugadoresViewModel.seleccionar(jugador);

            //le pasamos la informacion obtenida al viewmodel de jugadoresMiTM
            //jugadoresMiTMViewModel.actualizar(nombre, dorsal, imagenSeleccionada);

            //para volver atras
            navController.popBackStack();
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
