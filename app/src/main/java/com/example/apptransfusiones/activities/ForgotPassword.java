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
import com.example.apptransfusiones.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth auth;
    private Dialog dialogError;
    private Dialog dialogSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        //Inicializar el custom dialog success
        dialogSuccess = new Dialog(ForgotPassword.this);
        dialogSuccess.setContentView(R.layout.custom_dialog_success);
        dialogSuccess.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogSuccess.setCancelable(true);
        dialogSuccess.getWindow().getAttributes().windowAnimations = R.style.animation;

        //Inicializar el custom dialog error
        dialogError = new Dialog(ForgotPassword.this);
        dialogError.setContentView(R.layout.custom_dialog_error);
        dialogError.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogError.setCancelable(true);
        dialogError.getWindow().getAttributes().windowAnimations = R.style.animation;

        binding.buttonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        String email = binding.editTextEmail.getText().toString();
        if (!esEmailValido(email)) {
            showAlertEmailError();
        } else if (email.isEmpty()) {
            Toast.makeText(this, R.string.rellenarCampos, Toast.LENGTH_LONG).show();
        }
        sendEmail(email);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForgotPassword.this, InitSesionActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendEmail(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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

    private void showAlertSuccess() {
        dialogSuccess.show();
        TextView tvTitulo = dialogSuccess.findViewById(R.id.tv_titulo);
        tvTitulo.setText(R.string.exitoEmail);
        dialogSuccess.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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