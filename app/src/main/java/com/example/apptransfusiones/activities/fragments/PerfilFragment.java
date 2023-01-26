package com.example.apptransfusiones.activities.fragments;

import static androidx.core.app.ActivityCompat.recreate;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.activities.GoogleMapsActivity;
import com.example.apptransfusiones.activities.ModifyActivity;
import com.example.apptransfusiones.activities.otherClasses.ListAdapterPerfil;
import com.example.apptransfusiones.activities.otherClasses.ListElement;
import com.example.apptransfusiones.activities.otherClasses.RecyclerViewInterface;
import com.example.apptransfusiones.databinding.ActivityHomeBinding;
import com.example.apptransfusiones.databinding.FragmentPerfilBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

public class PerfilFragment extends Fragment implements RecyclerViewInterface {

    private FragmentPerfilBinding binding;
    private ActivityHomeBinding bindingHome;
    private ArrayList<ListElement> lista;
    private FirebaseFirestore database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentPerfilBinding.inflate(getLayoutInflater(), container, false);
        bindingHome = ActivityHomeBinding.inflate(getLayoutInflater());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database = FirebaseFirestore.getInstance();

        iniciarRecyclerView();

        ListAdapterPerfil adapter = new ListAdapterPerfil(lista, getContext(), this);
        binding.recyclerView.setAdapter(adapter);

        binding.buttonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });


        Bundle nuevoBundle = getArguments();
        String email = nuevoBundle.getString("email");
        /*
        database.collection("usuarios").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String nombreRecibido = (String) documentSnapshot.get("nombre");
                    binding.tvNombre.setText(nombreRecibido);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

         */

        return binding.getRoot();
    }

    private void iniciarRecyclerView() {
        lista = new ArrayList<>();
        lista.add(new ListElement("Modificar datos personales", R.drawable.vector_asset_edit));
        lista.add(new ListElement("Geolocalización del donante", R.drawable.vector_asset_location));
    }

    private void showChangeLanguageDialog() {
        final String[] listaItems = {"English", "Español", "Valencià"};
        AlertDialog.Builder mybuilder = new AlertDialog.Builder(getActivity());
        mybuilder.setTitle(R.string.cambiarIdioma);
        mybuilder.setSingleChoiceItems(listaItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate(getActivity());
                    dialog.dismiss();
                }
                if (i == 1) {
                    setLocale("es");
                    recreate(getActivity());
                    dialog.dismiss();
                }
                if (i == 2) {
                    setLocale("ca");
                    recreate(getActivity());
                    dialog.dismiss();
                }
            }
        });
        AlertDialog mydialog = mybuilder.create();
        mydialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onItemClick(int position) {
        if (position == 0) {
            Intent intent = new Intent(getContext(), ModifyActivity.class);
            Bundle nbundle = getArguments();
            String email = nbundle.getString("email");
            intent.putExtra("email", email);
            startActivity(intent);
        }
        if (position == 1) {
            Intent intent = new Intent(getContext(), GoogleMapsActivity.class);
            startActivity(intent);
        }
    }

}