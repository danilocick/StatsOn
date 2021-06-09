package com.example.mp07_statson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Usuario;
import com.example.mp07_statson.databinding.FragmentLoginBinding;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.SetOptions;

import java.util.Objects;
import java.util.concurrent.Executor;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;


public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLoginBinding.inflate(inflater, container, false)).getRoot();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.google.setOnClickListener(view1 -> signInClientGoogle.launch(GoogleSignIn.getClient(requireActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestIdToken(getString(R.string.default_web_client_id)).build()).getSignInIntent()));

        binding.facebook.setOnClickListener(view1 -> {});

        binding.login.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            String email = String.valueOf(binding.email.getText());
            String con = String.valueOf(binding.contrasenya.getText());
            if ( email.isEmpty() || con.isEmpty() ){
                Toast.makeText(requireActivity().getApplicationContext(), "Añade correo y contraseña", Toast.LENGTH_LONG).show();
                binding.progressBar.setVisibility(View.INVISIBLE);
            }else singingWithPasswd();
        });

        binding.resetPassword.setOnClickListener(v->{
            nav.navigate(R.id.action_loginFragment_to_resetPasswordFragment);
        });

        binding.crearCuenta.setOnClickListener(v -> nav.navigate(R.id.action_loginFragment_to_createAccountFragment));
        binding.progressBar.setIndeterminateDrawable(new Circle());
    }

    private void singingWithPasswd() {
        auth.signInWithEmailAndPassword(binding.email.getText().toString(), binding.contrasenya.getText().toString()).addOnCompleteListener(task -> {
            binding.progressBar.setVisibility(View.INVISIBLE);

            if (task.isSuccessful()) {
                acceder();
            } else {
                Toast.makeText(requireActivity().getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void acceder() {
        Toast.makeText(requireActivity().getApplicationContext(), "Access permitted", Toast.LENGTH_LONG).show();
        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).set(new Usuario(auth.getCurrentUser().getEmail()), SetOptions.merge());
        nav.navigate(R.id.action_loginFragment_to_menuFragment);
    }

    ActivityResultLauncher<Intent> signInClientGoogle = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        try {
            auth.signInWithCredential(GoogleAuthProvider.getCredential(Objects.requireNonNull(GoogleSignIn.getSignedInAccountFromIntent(result.getData()).getResult(ApiException.class))
                    .getIdToken(), null)).addOnSuccessListener(authResult -> acceder());
        } catch (ApiException e) {
            Toast.makeText(requireActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    });
}