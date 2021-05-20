package com.example.mp07_statson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.databinding.FragmentOutputMatchesBinding;

import org.xmlpull.v1.XmlPullParser;

public class OutputMatchesFragment extends BaseFragment {

    private FragmentOutputMatchesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentOutputMatchesBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (Jugador j : partidoviewmodel.jugadoresEquipoLocal) {

            String[] header = {"Dorsal", "Nombre", "Min", "PTS", "TL", "","","T2", "","","T3","","", "Rebotes","","", "Faltas","", "Balones","", "Tapones","",
                    "Pasos", "Dobles", "V.T", "As", "Val"};

            TableRow row=new TableRow(requireActivity());
            String dorsal = String.valueOf(j.dorsal);
            String nombre = String.valueOf(j.nombre);
            String puntos = String.valueOf(j.puntos);
            TextView tv1=new TextView(requireActivity());
            tv1.setText(dorsal);
            TextView tv2=new TextView(requireActivity());
            tv2.setText(puntos);

            TextView tv3=new TextView(requireActivity());
            tv3.setText(nombre);
//            TextView tv1=new TextView(requireActivity());
//            tv1.setText(dorsal);
//            TextView tv1=new TextView(requireActivity());
//            tv1.setText(dorsal);
//            TextView tv1=new TextView(requireActivity());
//            tv1.setText(dorsal);
//            row.addView(tv1);
//            row.addView(tv2);

            binding.table.addView(row);
        }

        binding.botonComeBack.setOnClickListener(view1 -> nav.popBackStack());


    }
}