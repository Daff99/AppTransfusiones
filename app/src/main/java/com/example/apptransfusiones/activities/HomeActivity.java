package com.example.apptransfusiones.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.activities.fragments.CitasFragment;
import com.example.apptransfusiones.activities.fragments.EventosFragment;
import com.example.apptransfusiones.activities.fragments.HomeFragment;
import com.example.apptransfusiones.activities.fragments.ManualFragment;
import com.example.apptransfusiones.activities.fragments.NotificationsFragment;
import com.example.apptransfusiones.activities.fragments.PerfilFragment;
import com.example.apptransfusiones.activities.fragments.RankingFragment;
import com.example.apptransfusiones.activities.fragments.TarjetaFragment;
import com.example.apptransfusiones.databinding.ActivityHomeBinding;
import com.example.apptransfusiones.databinding.NavHeaderBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityHomeBinding binding;
    private FirebaseAuth auth;
    private NavHeaderBinding bindingNavHeader;
    private FirebaseFirestore database;

    @Override
    protected void onStart() {
        super.onStart();
        /*
        String email = getIntent().getStringExtra("email");
        database.collection("usuarios").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String nombreRecibido = (String) documentSnapshot.get("nombre");
                    View v = binding.navigationView.getHeaderView(0);
                    bindingNavHeader = NavHeaderBinding.bind(v);
                    bindingNavHeader.navheaderNombre.setText(nombreRecibido);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

         */

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        bindingNavHeader = NavHeaderBinding.inflate(getLayoutInflater());

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        //Cada vez que abra la aplicación, yo quiero que se muestre primero el homeFragment
        replaceFragment(new HomeFragment());

        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.navigationView.setNavigationItemSelectedListener(this);

        binding.ivBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentM = getSupportFragmentManager();
                Fragment fragmentoActual = fragmentM.findFragmentById(R.id.frameLayout);
                if (!(fragmentoActual instanceof NotificationsFragment)) {
                    replaceFragment(new NotificationsFragment());
                } else {
                    fragmentM.popBackStack();
                }
            }
        });

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.citas:
                    replaceFragment(new CitasFragment());
                    break;
                case R.id.perfil:
                    replaceFragment(new PerfilFragment());
                    break;
                case R.id.tarjeta:
                    replaceFragment(new TarjetaFragment());
                    break;
                case R.id.eventos:
                    replaceFragment(new EventosFragment());
                    break;
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment) {
        String email = getIntent().getStringExtra("email");
        Bundle bundle = new Bundle();
        bundle.putString("email", email);

        fragment.setArguments(bundle);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                replaceFragment(new HomeFragment());
                binding.bottomNavigation.setSelectedItemId(R.id.home);
                break;
            case R.id.nav_logout:
                auth.signOut();
                Intent intent = new Intent(HomeActivity.this, InitSesionActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_eventos:
                replaceFragment(new EventosFragment());
                binding.bottomNavigation.setSelectedItemId(R.id.eventos);
                break;
            case R.id.nav_manual:
                replaceFragment(new ManualFragment());
                break;
            case R.id.nav_ranking:
                replaceFragment(new RankingFragment());
                break;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}