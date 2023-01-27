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
import com.example.apptransfusiones.databinding.ListElementCitasBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CitasFragment extends Fragment {

    private FragmentCitasBinding binding;
    private ListElementCitasBinding bindingCitas;
    private ArrayList<ListElementCitas> lista;
    private final String FILE_NAME = "citas.txt";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentCitasBinding.inflate(getLayoutInflater(), container, false);
        binding.rvCitas.setLayoutManager(new LinearLayoutManager(getContext()));

        bindingCitas = ListElementCitasBinding.inflate(getLayoutInflater(), container, false);

        String archivos [] = getContext().fileList();

        if (existeArchivo(archivos, FILE_NAME)) {
            try {
                InputStreamReader ins = new InputStreamReader(getContext().openFileInput(FILE_NAME));
                BufferedReader br = new BufferedReader(ins);
                String linea = br.readLine(); //Leo la primera linea de texto de mi fichero
                int lineIndex = 1;
                boolean encontrado = false;
                bindingCitas.tvFechaCita.setText(linea);
                while (linea != null && !encontrado) {
                    if (lineIndex % 2 == 0) {
                        encontrado = true;
                        break;
                    }
                    lineIndex ++;
                }
                bindingCitas.tvHoraCita.setText(linea);
                br.close();
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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

    private boolean existeArchivo(String archivos [], String nombre) {
        for (int i = 0; i < archivos.length; i ++) {
            if (nombre.equals(archivos[i])) {
                return true;
            }
        }
        return false;
    }
}