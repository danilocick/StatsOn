package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp07_statson.ViewModel.EquipoViewModel;
import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;

public class OutputMatchesFragment extends Fragment {

    private FragmentOutputMatchesBinding binding;
    private NavController navController;
    private EquipoViewModel equipoViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOutputMatchesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.tableInvoices.setStretchAllColumns(true);
        
        equipoViewModel = new ViewModelProvider(requireActivity()).get(EquipoViewModel.class);
//        if (nombreRivalViewModel.seleccionado() != null){
//            nombreRivalViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.equiporival.setText(a));
//        }

        //ComeBack
        binding.botonComeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

    }


}