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
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mp07_statson.databinding.FragmentLoadBinding;
import com.example.mp07_statson.databinding.FragmentMenuBinding;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.romainpiel.shimmer.Shimmer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoadFragment extends Fragment {

    Executor executor = Executors.newSingleThreadExecutor();
    NavController navController;
    private FragmentLoadBinding binding;
    private Shimmer shimmer = new Shimmer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentLoadBinding.inflate(inflater, container, false)).getRoot();

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
                navController.navigate(R.id.action_loadFragment_to_menuFragment);
            }
        });


        // esto deberia estar en el Model y llamarlo a traves del ViewModel
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //shimmer
                    shimmer.start(binding.shimmer);
                    shimmer.setDirection(Shimmer.ANIMATION_DIRECTION_LTR);

                    // simular la carga de recursos
                    Thread.sleep(5000);
                    finishedLoading.postValue(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });




        //progressBar
//        Sprite doubleBounce = new WanderingCubes();
//        binding.progressBar.setIndeterminateDrawable(doubleBounce);
    }
}