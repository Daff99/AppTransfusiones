package com.example.apptransfusiones.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.activities.otherClasses.ListAdapterHorizontal;
import com.example.apptransfusiones.activities.otherClasses.ListElementHorizontal;
import com.example.apptransfusiones.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<ListElementHorizontal> lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        binding.recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        iniciarRecyclerView();

        ListAdapterHorizontal adapter = new ListAdapterHorizontal(lista, getContext());
        binding.recyclerViewHorizontal.setAdapter(adapter);

        return binding.getRoot();
    }

    private void iniciarRecyclerView() {
        lista = new ArrayList<>();
        lista.add(new ListElementHorizontal("Este es el primer elemento", R.drawable.recycler_horizontal_1));
        lista.add(new ListElementHorizontal("Este es el segundo elemento", R.drawable.recycler_horizontal_2));
        lista.add(new ListElementHorizontal("Este es el tercer elemento", R.drawable.medalla_perfil));
        lista.add(new ListElementHorizontal("Este es el cuarto elemento", R.drawable.medalla_perfil));
        lista.add(new ListElementHorizontal("Este es el quinto elemento", R.drawable.medalla_perfil));
    }
}