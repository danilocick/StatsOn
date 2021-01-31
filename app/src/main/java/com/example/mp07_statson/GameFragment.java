package com.example.mp07_statson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.mp07_statson.ViewModel.NombreRivalViewModel;
import com.example.mp07_statson.databinding.FragmentGameBinding;
import com.example.mp07_statson.databinding.PopupAsistenciaBinding;

import static com.example.mp07_statson.R.layout.popup_asistencia;

public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
    private NavController navController;
    private NombreRivalViewModel nombreRivalViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentGameBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //setText NOMBRE RIVAL
        nombreRivalViewModel = new ViewModelProvider(requireActivity()).get(NombreRivalViewModel.class);
        nombreRivalViewModel.seleccionado().observe(getViewLifecycleOwner(), a -> binding.titleTeamB.setText(a));

        //Load PopUps
        //POPUP
        View popupViewAsistencia = LayoutInflater.from(getActivity()).inflate(popup_asistencia, null);
        final PopupWindow popupWindowAsistencia = new PopupWindow(popupViewAsistencia, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindowAsistencia.setOutsideTouchable(true);
        popupWindowAsistencia.setFocusable(true);

        //AcabarPartido
        binding.botonAcabarPartido.setOnClickListener(view16 -> navController.navigate(R.id.action_gameFragment_to_menuFragment));
        //vista previa
        binding.botonVistaPrevia.setOnClickListener(view15 -> navController.navigate(R.id.action_gameFragment_to_outputMatchesFragment));


        binding.jugB1.setOnClickListener(v->{

            binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_white_red);

            binding.imagenThreePointMore.setOnClickListener(view1 -> {
                popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER,0,0);
                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });

            binding.imagenThreePointLess.setOnClickListener(view1 -> {
                popupWindowAsistencia.showAtLocation(popupViewAsistencia, Gravity.CENTER,0,0);
                binding.jugB1.setBackgroundResource(R.drawable.recyclerv_round_greydark_black);
            });

        });


    }

    //clase para acceder a los campos de viewholder_jugador_miteam
    static class PopUp extends PopupWindow{
        PopupAsistenciaBinding binding;

        public PopUp(@NonNull PopupAsistenciaBinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }

}