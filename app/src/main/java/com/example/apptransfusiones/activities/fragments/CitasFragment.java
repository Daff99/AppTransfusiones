package com.example.apptransfusiones.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.activities.otherClasses.ListAdapterCitas;
import com.example.apptransfusiones.activities.otherClasses.ListElementCitas;
import com.example.apptransfusiones.databinding.FragmentCitasBinding;

import java.util.ArrayList;

public class CitasFragment extends Fragment {

    private FragmentCitasBinding binding;
    private ArrayList<ListElementCitas> lista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentCitasBinding.inflate(getLayoutInflater(), container, false);
        binding.rvCitas.setLayoutManager(new LinearLayoutManager(getContext()));

        iniciarRecyclerView();

        ListAdapterCitas adapter = new ListAdapterCitas(lista, getContext());
        binding.rvCitas.setAdapter(adapter);

        binding.buttonAgregarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentSecondCitas());
            }
        });

        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null).commit();
    }

    private void iniciarRecyclerView() {
        lista = new ArrayList<>();
        lista.add(new ListElementCitas("Antiguo Hospital", "Carrer de l´Hospital, 46001", "Valencia"));
        lista.add(new ListElementCitas("Hospital 12 de octubre", "Avenida de Córdoba, 28041", "Madrid"));
        lista.add(new ListElementCitas("Hospital Gregorio Marañón", "Calle Dr Esquerdo 46, 28007", "Madrid"));
    }
}