package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mp07_statson.databinding.FragmentLoadBinding;
import com.example.mp07_statson.databinding.FragmentMenuPNuevoBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MenuPNuevoFragment extends Fragment {

    Executor executor = Executors.newSingleThreadExecutor();
    NavController navController;
    private FragmentMenuPNuevoBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMenuPNuevoBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // esta variable deberia estar en un ViewModel
        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();


        finishedLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //navController.navigate(R.id.action_menuPNuevoFragment_to_rivalFragment);
            }
        });

        // esto deberia estar en el Model y llamarlo a traves del ViewModel
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // simular la carga de recursos
                    Thread.sleep(500);
                    finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}