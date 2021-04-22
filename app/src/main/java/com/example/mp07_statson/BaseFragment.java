package com.example.mp07_statson;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class BaseFragment extends Fragment {
    NavController nav;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseStorage stor;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
