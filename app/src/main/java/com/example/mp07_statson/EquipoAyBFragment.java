package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.mp07_statson.databinding.FragmentEquipoAyBBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class EquipoAyBFragment extends BaseFragment {
    private FragmentEquipoAyBBinding binding;

    private int locales, visitantes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentEquipoAyBBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                    default:
                        return new EquipoAFragment();
                    case 1:
                        return new equipoBFragment();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                default:
                    tab.setText(viewmodel.nombreEquipoLocal);
                    break;
                case 1:
                    tab.setText(viewmodel.nombreEquipoVisitante);
                    break;
            }
        }).attach();

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        binding.siguiente.setOnClickListener(view12 ->{

            locales = 0;
            visitantes = 0;

            for (int i = 0; i < partidoviewmodel.jugadoresEquipoLocal.size(); i++) {
                if (partidoviewmodel.jugadoresEquipoLocal.get(i).starter) locales++;
                System.out.println("l: "+locales);
            }

            for (int i = 0; i < partidoviewmodel.jugadoresEquipoVisitante.size(); i++) {
                if (partidoviewmodel.jugadoresEquipoVisitante.get(i).starter) visitantes++;
                System.out.println("v: "+visitantes);
            }

            if (locales==5 && visitantes==5){
                nav.navigate(R.id.action_equipoAyBFragment_to_gameFragment);
            }else{} //ERROR MESSAGE

        });
    }
}