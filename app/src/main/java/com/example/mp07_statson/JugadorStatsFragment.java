package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.FirebaseVar;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.Model.Ppp;
import com.example.mp07_statson.databinding.FragmentJugadorStatsBinding;
import com.google.firebase.firestore.DocumentSnapshot;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class JugadorStatsFragment extends BaseFragment {

    private FragmentJugadorStatsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentJugadorStatsBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());

        binding.botonEditTM.setOnClickListener(view12 -> nav.navigate(R.id.action_jugadorStatsFragment_to_menuFragment));

        binding.nombreJugador.setText(viewmodel.jugadorSeleccionado.nombre);
        binding.dorsalJugador.setText(String.valueOf(viewmodel.jugadorSeleccionado.dorsal));
        Glide.with(requireView()).load(viewmodel.jugadorSeleccionado.imagen).into(binding.imagenJugador);

        db.collection(FirebaseVar.USUARIOS).document(auth.getUid()).collection(FirebaseVar.EQUIPOS).document(viewmodel.idEquipoSeleccionado).collection(FirebaseVar.JUGADORES).document(viewmodel.jugadorSeleccionado.idJugador)
                .collection(FirebaseVar.PPP).addSnapshotListener((value, error) -> {
                    List<Ppp> pppJugador = new ArrayList<>();
                    for(DocumentSnapshot documentSnapshot: value){
                        pppJugador.add(documentSnapshot.toObject(Ppp.class));
                    }
                    viewmodel.pppJugador = new ArrayList<>();
                    viewmodel.pppJugador = pppJugador;
                    cargarDatosJugador();
                });
    }

    private void cargarDatosJugador() {
        //STATSBARRAS
        BarChart mBarChart = binding.barchart;

        for (Ppp ppp: viewmodel.pppJugador) {
            int red;
            if (ppp.puntos >10 && ppp.puntos < 25){
                red= Color.GRAY;
            } else if (ppp.puntos >=25) {
                red = Color.DKGRAY;
            }else red= Color.LTGRAY;
            mBarChart.addBar(new BarModel(ppp.nombre, ppp.puntos,red));
        }
        mBarChart.startAnimation();

        //STATSQUESO
        binding.t2Piechart.addPieSlice(new PieModel("T2 Fallados", viewmodel.jugadorSeleccionado.t2menos, Color.parseColor("#FF7F7F")));
        binding.t2Piechart.addPieSlice(new PieModel("T2 Anotados", viewmodel.jugadorSeleccionado.t2mas, Color.parseColor("#85182A")));

        binding.t3Piechart.addPieSlice(new PieModel("T3 Fallados", viewmodel.jugadorSeleccionado.t3menos, Color.parseColor("#FF7F7F")));
        binding.t3Piechart.addPieSlice(new PieModel("T3 Anotados", viewmodel.jugadorSeleccionado.t3mas, Color.parseColor("#85182A")));

        binding.tlPiechart.addPieSlice(new PieModel("TL Fallados", viewmodel.jugadorSeleccionado.t1menos, Color.parseColor("#FF7F7F")));
        binding.tlPiechart.addPieSlice(new PieModel("TL Anotados", viewmodel.jugadorSeleccionado.t1mas, Color.parseColor("#85182A")));

        binding.t2Piechart.startAnimation();
        binding.t3Piechart.startAnimation();
        binding.tlPiechart.startAnimation();
    }

}