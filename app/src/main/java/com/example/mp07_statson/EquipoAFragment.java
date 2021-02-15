package com.example.mp07_statson;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mp07_statson.Model.Jugador;
import com.example.mp07_statson.ViewModel.JugadoresViewModel;
import com.example.mp07_statson.databinding.FragmentEquipoABinding;
import com.example.mp07_statson.databinding.ViewholderJugadorEquipoABinding;


import java.util.List;


public class EquipoAFragment extends Fragment {

    private NavController navController;
    private FragmentEquipoABinding binding;
    private JugadoresViewModel jugadoresViewModel;
    JugadorAdapter jugadorAdapter = new JugadorAdapter();
    int starts=0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (binding = FragmentEquipoABinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        jugadoresViewModel = new ViewModelProvider(requireActivity()).get(JugadoresViewModel.class);

        binding.botonSiguienteTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    navController.navigate(R.id.action_equipoAFragment_to_equipoBFragment);
//                if (starts == 5){
//                    starts = 0;
//                }else{
//                    // If name is not entered
//                    Toast.makeText(requireActivity().getApplicationContext(), "Select 5 Stars, Click Photo", Toast.LENGTH_LONG).show();
//                }
            }
        });

        binding.botonComeBackTeamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //para volver atras
                navController.popBackStack();
            }
        });

        binding.listaJugadoresTeamA.setAdapter(jugadorAdapter);
        //acceder al viewModel
        //printar jugadores
        //TODO: int m que se coja bien el numero, sin errores.
        int m = 0;
        jugadoresViewModel.obtenerJugadoresDeEquipo(m).observe(getViewLifecycleOwner(), jugadorAdapter::establecerjugadores);
    }

    class JugadorAdapter extends RecyclerView.Adapter<JugadorViewHolder>{

        List<Jugador> jugadorList;

        @NonNull
        @Override
        public JugadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JugadorViewHolder(ViewholderJugadorEquipoABinding.inflate(getLayoutInflater()), parent, false);
        }

        @Override
        public void onBindViewHolder(@NonNull JugadorViewHolder holder, int position) {
            Jugador jugador = jugadorList.get(position);
            Glide.with(holder.itemView).load(jugador.imagen).into(holder.binding.imagenJugador);
            holder.binding.nombreJugador.setText(jugador.nombre);
            holder.binding.dorsalJugador.setText(String.valueOf(jugador.dorsal));

            holder.binding.background.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int color = Color.TRANSPARENT;
                    Drawable background = holder.binding.background.getBackground();
                    if (background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    //para volver atras
                    if (color ==Color.rgb(0,0,0) && starts < 5){
                        holder.binding.background.setBackgroundColor(Color.rgb(218,165,32));
                        starts++;
                        System.out.println(starts);
                    }else {
                        if (starts == 5){
                            Toast.makeText(requireActivity().getApplicationContext(), "You have 5 Stars", Toast.LENGTH_LONG).show();
                        }else {
                            holder.binding.background.setBackgroundColor(Color.rgb(0, 0, 0));
                            starts--;
                            System.out.println(starts);
                        }
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return jugadorList == null ? 0 : jugadorList.size();
        }

        void establecerjugadores(List<Jugador> jugadorList){
            this.jugadorList = jugadorList;
            notifyDataSetChanged();
        }
    }

    //clase para acceder a los campos de viewholder_jugador_miteam
    static class JugadorViewHolder extends RecyclerView.ViewHolder{
        ViewholderJugadorEquipoABinding binding;

        public JugadorViewHolder(@NonNull ViewholderJugadorEquipoABinding binding, ViewGroup parent, boolean b){
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}