package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.databinding.FragmentJugadorStatsBinding;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

public class JugadorStatsFragment extends BaseFragment {

    private FragmentJugadorStatsBinding binding;
    int T2F, T2A, T3A, T3F, TLA, TLF;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentJugadorStatsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        viewmodel.seleccionado().observe(getViewLifecycleOwner(), elemento -> {
//            Glide.with(JugadorStatsFragment.this).load(elemento.imagen).into(binding.imagenJugador);
//            binding.nombreJugador.setText(elemento.nombre);
//            binding.dorsalJugador.setText(String.valueOf(elemento.dorsal));
//            T2A = elemento.t2mas;
//            T2F = elemento.t2menos;
//            T3A = elemento.t3mas;
//            T3F = elemento.t3menos;
//            TLA = elemento.t1mas;
//            TLF = elemento.t1menos;
//        });

        binding.botonComeBack.setOnClickListener(view1 -> {nav.popBackStack();});

        binding.botonEditTM.setOnClickListener(view12 -> {nav.navigate(R.id.action_jugadorStatsFragment_to_editJugadorFragment);});

        cargarDatosJugador();
    }

    private void cargarDatosJugador() {
        //STATSBARRAS
        BarChart mBarChart = (BarChart) binding.barchart;

        mBarChart.addBar(new BarModel("Mina",7, 0xFF123456 ));
        mBarChart.addBar(new BarModel("Neus",7,  0xFF343456));
        mBarChart.addBar(new BarModel("Grama",9, 0xFF563456));
        mBarChart.addBar(new BarModel("Ametlla",11, 0xFF873F56));
        mBarChart.addBar(new BarModel("Sant Josep",33, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("UBSA",2,  0xFF343456));
        mBarChart.addBar(new BarModel("Sant Andreu",7, 0xFF1FF4AC));
        mBarChart.addBar(new BarModel("Dani",11,  0xFF1BA4E6));

        mBarChart.startAnimation();
        //STATSQUESO
        PieChart mPieChart;
        PieChart mPieChart2;
        PieChart mPieChart3;
        mPieChart = (PieChart) binding.piechart;
        mPieChart2 = (PieChart) binding.piechart2;
        mPieChart3 = (PieChart) binding.piechart3;

        mPieChart.addPieSlice(new PieModel("T2 Fallados", T2F, Color.parseColor("#FF7F7F")));
        mPieChart.addPieSlice(new PieModel("T2 Anotados", T2A, Color.parseColor("#85182A")));

        mPieChart2.addPieSlice(new PieModel("T3 Fallados", T3F, Color.parseColor("#FF7F7F")));
        mPieChart2.addPieSlice(new PieModel("T3 Anotados", T3A, Color.parseColor("#85182A")));

        mPieChart3.addPieSlice(new PieModel("TL Fallados", TLF, Color.parseColor("#FF7F7F")));
        mPieChart3.addPieSlice(new PieModel("TL Anotados", TLA, Color.parseColor("#85182A")));

        mPieChart.startAnimation();
        mPieChart2.startAnimation();
        mPieChart3.startAnimation();
    }

}