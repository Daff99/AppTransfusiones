package com.example.apptransfusiones.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apptransfusiones.databinding.FragmentTarjetaBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TarjetaFragment extends Fragment {

    private FragmentTarjetaBinding binding;
    private FirebaseFirestore database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentTarjetaBinding.inflate(getLayoutInflater(), container, false);

        database = FirebaseFirestore.getInstance();

        Bundle nuevoBundle = getArguments();
        String email = nuevoBundle.getString("email");
        /*
        database.collection("usuarios").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String nombreRecibido = (String) documentSnapshot.get("nombre");
                    binding.tvNombre.setText(nombreRecibido);
                    binding.tvNombreCardView.setText(nombreRecibido);
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
}