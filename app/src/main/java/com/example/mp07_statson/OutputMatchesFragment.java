package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;

public class OutputMatchesFragment extends BaseFragment {

    private FragmentOutputMatchesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOutputMatchesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tableInvoices.setStretchAllColumns(true);

        //ComeBack
        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

    }
}