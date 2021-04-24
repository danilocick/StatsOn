package com.example.mp07_statson;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mp07_statson.ViewModel.StatsOnViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class BaseFragment extends Fragment {
    public NavController nav;
    public FirebaseAuth auth;
    public FirebaseFirestore db;
    public FirebaseStorage stor;
    public StatsOnViewModel viewmodel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewmodel = new ViewModelProvider(requireActivity()).get(StatsOnViewModel.class);
        if(getParentFragment() != null) {
            nav = Navigation.findNavController(getParentFragment().getView());
        } else {
            nav = Navigation.findNavController(view);
        }
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        stor = FirebaseStorage.getInstance();
    }
}
