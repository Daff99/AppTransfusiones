package com.example.apptransfusiones.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.databinding.ActivityInitSesionBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InitSesionActivity extends AppCompatActivity {

    private ActivityInitSesionBinding binding;
    private FirebaseAuth auth;
    private static final int rc = 100;
    private FirebaseFirestore database;
    private Dialog dialogError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityInitSesionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        //Inicializar el custom dialog error
        dialogError = new Dialog(InitSesionActivity.this);
        dialogError.setContentView(R.layout.custom_dialog_error);
        dialogError.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogError.setCancelable(true);
        dialogError.getWindow().getAttributes().windowAnimations = R.style.animation;

        //Show Hide Password using Eye Icon
        binding.hidePassword.setImageResource(R.drawable.vector_asset_eye);
        binding.hidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    //Si la contraseña es visible la ocultamos
                    binding.editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    binding.editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.hidePassword.setImageResource(R.drawable.vector_asset_eye);
                }
            }
        });

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if ((email.isEmpty()) || (password.isEmpty())) {
                    Toast.makeText(InitSesionActivity.this, R.string.rellenarCampos, Toast.LENGTH_LONG).show();
                } else {
                    loginUser(email, password);
                }
            }
        });

        binding.tvPreguntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitSesionActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitSesionActivity.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(InitSesionActivity.this, HomeActivity.class);
                    String email = binding.editTextEmail.getText().toString();
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (!esEmailValido(email)) {
                    showAlertEmailError();
                } else
                    showAlertDialogError();
            }
        });
    }

    private void showAlertEmailError() {
        dialogError.show();
        TextView tvTitulo = dialogError.findViewById(R.id.tv_titulo_error);
        TextView tvCuerpo = dialogError.findViewById(R.id.tv_cuerpo_error);
        tvTitulo.setText(R.string.formatoNoValido);
        tvCuerpo.setText(R.string.introduceEmailDeNuevo);
        dialogError.findViewById(R.id.btn_cancel_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });
        dialogError.findViewById(R.id.btn_okay_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });
    }

    private void showAlertDialogError() {
        dialogError.show();
        TextView tvTitulo = dialogError.findViewById(R.id.tv_titulo_error);
        TextView tvCuerpo = dialogError.findViewById(R.id.tv_cuerpo_error);
        tvTitulo.setText(R.string.errorEmailPassword);
        tvCuerpo.setText(R.string.credenciales);
        dialogError.findViewById(R.id.btn_cancel_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });
        dialogError.findViewById(R.id.btn_okay_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogError.dismiss();
            }
        });
    }

    private boolean esEmailValido(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
}