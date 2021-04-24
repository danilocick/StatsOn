package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentOptionsBinding;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

public class OptionsFragment extends BaseFragment {
    private FragmentOptionsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentOptionsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

        binding.botonComeBackOpc.setOnClickListener(view1 -> {nav.popBackStack();});

        binding.botonGuardarOpc.setOnClickListener(view12 -> nav.popBackStack());

        binding.expansionLayoutMinutos.addListener((expansionLayout, expanded) -> {
            expansionsCollection.openOnlyOne(true);
        });

        binding.cerrarSesion.setOnClickListener(v->{
            auth.getInstance().signOut();
            nav.navigate(R.id.action_optionsFragment_to_loginFragment);
        });

    }
}