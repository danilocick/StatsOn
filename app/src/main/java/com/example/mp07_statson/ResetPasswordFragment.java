package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mp07_statson.databinding.FragmentLoginBinding;
import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;
import com.example.mp07_statson.databinding.FragmentResetPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends DialogFragment {

    private FragmentResetPasswordBinding binding;
    private FirebaseAuth auth;
    private NavController nav;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentResetPasswordBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding.login.setOnClickListener(v->{
            if(String.valueOf(binding.email.getText()).equals("")){
                Toast.makeText(requireActivity().getApplicationContext(), "Ingrese correo", Toast.LENGTH_LONG).show();
            }else {
                auth.sendPasswordResetEmail(String.valueOf(binding.email.getText()));
                dismiss();
            }
        });
    }
}