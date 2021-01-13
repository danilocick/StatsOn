package com.example.mp07_statson;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.databinding.FragmentAddJugadorMTBinding;
import com.example.mp07_statson.databinding.FragmentJugadorStatsBinding;
import com.example.mp07_statson.databinding.ViewholderJugadorMiTeamBinding;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

public class JugadorStatsFragment extends Fragment {

    private NavController navController;
    private FragmentJugadorStatsBinding binding;
    private JugadoresMiTMViewModel jugadoresViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentJugadorStatsBinding.inflate(inflater, container, false)).getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresMiTMViewModel.class);

        //MutableLiveData<Jugador> jugador =jugadoresViewModel.seleccionado();
        jugadoresViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Jugador>() {
            @Override
            public void onChanged(Jugador elemento) {
                //Glide.with().load(elemento.imagen).into(binding.imagen);
                binding.nombreJugador.setText(elemento.nombre);
                binding.dorsalJugador.setText(elemento.dorsal);
            }
        });


        //ComeBack
        binding.botonComeBackSeeTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        //IrEditar
        binding.botonEditTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.navigate(R.id.action_jugadorStatsFragment_to_editJugadorFragment);
            }
        });

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

        mPieChart.addPieSlice(new PieModel("Tiros Anotados", 15, Color.parseColor("#85182A")));
        mPieChart.addPieSlice(new PieModel("Tiros Fallados", 9, Color.parseColor("#FF7F7F")));

        mPieChart2.addPieSlice(new PieModel("Triples Anotados", 2, Color.parseColor("#85182A")));
        mPieChart2.addPieSlice(new PieModel("Triples Fallados", 3, Color.parseColor("#FF7F7F")));

        mPieChart3.addPieSlice(new PieModel("TL Anotados", 12, Color.parseColor("#85182A")));
        mPieChart3.addPieSlice(new PieModel("TL Fallados", 2, Color.parseColor("#FF7F7F")));

        mPieChart.startAnimation();
        mPieChart2.startAnimation();
        mPieChart3.startAnimation();
    }

}