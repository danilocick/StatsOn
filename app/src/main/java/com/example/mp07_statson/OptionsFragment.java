package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mp07_statson.databinding.FragmentAddJugadorBinding;
import com.example.mp07_statson.databinding.FragmentOptionsBinding;
import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.google.firebase.auth.FirebaseAuth;

public class OptionsFragment extends Fragment {

    private NavController navController;
    private FragmentOptionsBinding binding;
    private FirebaseAuth mAuth;


    //add an ExpansionLayoutCollection to your recycler adapter
    final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOptionsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();


        //ComeBack
        binding.botonComeBackOpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //Guardar
        binding.botonGuardarOpc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //NEW EXPANSIONSPANEL
        binding.expansionLayoutMinutos.addListener(new ExpansionLayout.Listener() {
            @Override
            public void onExpansionChanged(ExpansionLayout expansionLayout, boolean expanded) {

            }
        });

        binding.cerrarSesion.setOnClickListener(v->{
            mAuth.getInstance().signOut();
        });

    }
}