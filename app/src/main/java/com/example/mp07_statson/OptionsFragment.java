package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentOptionsBinding;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class OptionsFragment extends BaseFragment {
    private FragmentOptionsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentOptionsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpansionLayoutCollection expansionLayoutCollection = new ExpansionLayoutCollection();
        expansionLayoutCollection.add(binding.expansionMinutosLayout);
        expansionLayoutCollection.add(binding.expansionPeriodosLayout);
        expansionLayoutCollection.add(binding.expansionMinPELayout);
        expansionLayoutCollection.openOnlyOne(true);

        binding.expansionMinutosLayout.addListener((expansionLayout, expanded) -> {
            binding.minutosCinco.setOnClickListener(v -> {
                binding.minutosInt.setText("5");
                expansionLayout.collapse(true);
            });
            binding.minutosSeis.setOnClickListener(v -> {
                binding.minutosInt.setText("6");
                expansionLayout.collapse(true);
            });
            binding.minutosDiez.setOnClickListener(v -> {
                binding.minutosInt.setText("10");
                expansionLayout.collapse(true);
            });
        });

        binding.expansionPeriodosLayout.addListener((expansionLayout, expanded) -> {
            binding.periodosCuatro.setOnClickListener(v -> {
                binding.periodosInt.setText("4");
                expansionLayout.collapse(true);
            });
            binding.periodosSeis.setOnClickListener(v -> {
                binding.periodosInt.setText("6");
                expansionLayout.collapse(true);
            });
            binding.periodosOcho.setOnClickListener(v -> {
                binding.periodosInt.setText("8");
                expansionLayout.collapse(true);
            });
        });

        binding.expansionMinPELayout.addListener((expansionLayout, expanded) -> {
            binding.minPETres.setOnClickListener(v -> {
                binding.minPEInt.setText("3");
                expansionLayout.collapse(true);
            });
            binding.minPECinco.setOnClickListener(v -> {
                binding.minPEInt.setText("5");
                expansionLayout.collapse(true);
            });
            binding.minPESeis.setOnClickListener(v -> {
                binding.minPEInt.setText("6");
                expansionLayout.collapse(true);
            });
        });

        binding.botonComeBackOpc.setOnClickListener(view1 -> {nav.popBackStack();});

        binding.cerrarSesion.setOnClickListener(v->{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

            auth.signOut();
            mGoogleSignInClient.revokeAccess();
            nav.navigate(R.id.action_optionsFragment_to_loginFragment);
        });

        binding.botonGuardarOpc.setOnClickListener(v -> {
            String minutos = String.valueOf(binding.minutosInt.getText());
            viewmodel.minutos = Integer.parseInt(minutos);
            String periodos = String.valueOf(binding.periodosInt.getText());
            viewmodel.periodos = Integer.parseInt(periodos);
            String minPE = String.valueOf(binding.minPEInt.getText());
            viewmodel.minutosPE = Integer.parseInt(minPE);

            nav.popBackStack();
        });
    }
}