package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.FirebaseVar;
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

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        binding.botonEditTM.setOnClickListener(view12 -> nav.navigate(R.id.action_jugadorStatsFragment_to_editJugadorFragment));

        binding.nombreJugador.setText(viewmodel.jugadorSeleccionado.nombre);
        binding.dorsalJugador.setText(String.valueOf(viewmodel.jugadorSeleccionado.dorsal));
        Glide.with(requireView()).load(viewmodel.jugadorSeleccionado.imagen).into(binding.imagenJugador);

        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.jugadorSeleccionado.idJugador)
                .collection(FirebaseVar.PPP).document(FirebaseVar.PUNTOS).get()
                .addOnSuccessListener(documentReference -> {
                    cargarDatosJugador();
                });
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
        PieChart t2pieChart, t3piechart, tlpiechart;
        t2pieChart = (PieChart) binding.t2Piechart;
        t3piechart = (PieChart) binding.t3Piechart;
        tlpiechart = (PieChart) binding.tlPiechart;

        t2pieChart.addPieSlice(new PieModel("T2 Fallados", viewmodel.jugadorSeleccionado.t2menos, Color.parseColor("#FF7F7F")));
        t2pieChart.addPieSlice(new PieModel("T2 Anotados", viewmodel.jugadorSeleccionado.t2mas, Color.parseColor("#85182A")));

        t3piechart.addPieSlice(new PieModel("T3 Fallados", viewmodel.jugadorSeleccionado.t3menos, Color.parseColor("#FF7F7F")));
        t3piechart.addPieSlice(new PieModel("T3 Anotados", viewmodel.jugadorSeleccionado.t3mas, Color.parseColor("#85182A")));

        tlpiechart.addPieSlice(new PieModel("TL Fallados", viewmodel.jugadorSeleccionado.t1menos, Color.parseColor("#FF7F7F")));
        tlpiechart.addPieSlice(new PieModel("TL Anotados", viewmodel.jugadorSeleccionado.t1mas, Color.parseColor("#85182A")));

        t2pieChart.startAnimation();
        t3piechart.startAnimation();
        tlpiechart.startAnimation();
    }

}