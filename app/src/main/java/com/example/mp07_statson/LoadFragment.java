package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.example.mp07_statson.databinding.FragmentLoadBinding;
import com.romainpiel.shimmer.Shimmer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoadFragment extends BaseFragment {

    Executor executor = Executors.newSingleThreadExecutor();
    private FragmentLoadBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLoadBinding.inflate(inflater, container, false)).getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Shimmer shimmer = new Shimmer();
        MutableLiveData<Boolean> finishedLoading = new MutableLiveData<>();

        finishedLoading.observe(getViewLifecycleOwner(), aBoolean -> {
            if (auth.getCurrentUser() == null) {
                nav.navigate(R.id.action_loadFragment_to_loginFragment);
            } else {
                nav.navigate(R.id.action_loadFragment_to_menuFragment);
            }
        });

        executor.execute(() -> {
            try {
                //shimmer
                shimmer.start(binding.shimmer);
                shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_LTR);

                // simular la carga de recursos
                Thread.sleep(50);
                finishedLoading.postValue(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}