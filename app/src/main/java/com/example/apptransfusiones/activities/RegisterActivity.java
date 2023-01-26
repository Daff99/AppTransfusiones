package com.example.apptransfusiones.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityRegisterBinding binding;
    private FirebaseFirestore database;
    private Dialog dialogError;
    private Dialog dialogSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Inicializar el custom dialog success
        dialogSuccess = new Dialog(RegisterActivity.this);
        dialogSuccess.setContentView(R.layout.custom_dialog_success);
        dialogSuccess.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogSuccess.setCancelable(true);
        dialogSuccess.getWindow().getAttributes().windowAnimations = R.style.animation;

        //Inicializar el custom dialog error
        dialogError = new Dialog(RegisterActivity.this);
        dialogError.setContentView(R.layout.custom_dialog_error);
        dialogError.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogError.setCancelable(true);
        dialogError.getWindow().getAttributes().windowAnimations = R.style.animation;

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        binding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                String password2 = binding.editTextRepitePassword.getText().toString();
                if ((email.isEmpty()) || (password.isEmpty()) || (password2.isEmpty()) || (nombre.isEmpty())) {
                    Toast.makeText(RegisterActivity.this, R.string.rellenarCampos, Toast.LENGTH_SHORT).show();
                } else if ((!password.equals(password2))) {
                    showAlertPasswords();
                } else if (!esEmailValido(email)) {
                    showAlertEmailError();
                } else {
                    guardarEnBD(nombre, email, password);
                    createAccount(email, password);
                }
            }
        });

        binding.tvPreguntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, InitSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void guardarEnBD(String nombre, String email, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("password", password);
        database.collection("usuarios").document(email).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegisterActivity.this, InitSesionActivity.class);
        startActivity(intent);
        finish();
    }

    private void createAccount(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showAlertSuccess();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showAlertDialogError();
                    }
                });
    }

    private void showAlertPasswords() {
        dialogError.show();
        TextView tvTitulo = dialogError.findViewById(R.id.tv_titulo_error);
        TextView tvCuerpo = dialogError.findViewById(R.id.tv_cuerpo_error);
        tvTitulo.setText(R.string.coincidenPasswords);
        tvCuerpo.setText(R.string.samePasswords);
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

    private void showAlertSuccess() {
        dialogSuccess.show();
        TextView tvTitulo = dialogSuccess.findViewById(R.id.tv_titulo);
        tvTitulo.setText(R.string.exitoRegistro);
        dialogSuccess.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                String email = binding.editTextEmail.getText().toString();
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
                dialogSuccess.dismiss();
            }
        });
        dialogSuccess.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuccess.dismiss();
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

    private boolean esEmailValido(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
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
}