package com.example.mp07_statson;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mp07_statson.databinding.FragmentCreateAccountBinding;
import com.example.mp07_statson.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountFragment extends Fragment {

    NavController navController;
    private FragmentCreateAccountBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentCreateAccountBinding.inflate(inflater, container, false)).getRoot();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();

        binding.login.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(), binding.contrasenya.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            binding.progressBar.setVisibility(View.INVISIBLE);

                            if (task.isSuccessful()) {
                                Toast.makeText(requireActivity().getApplicationContext(),
                                        "Registro Completado",
                                        Toast.LENGTH_LONG).show();
                                navController.popBackStack();

                            } else {
                                Toast.makeText(requireActivity().getApplicationContext(),
                                        task.getException().getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        });


    }
}