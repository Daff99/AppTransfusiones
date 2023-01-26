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
import com.example.apptransfusiones.databinding.ActivityModifyBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifyActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityModifyBinding binding;
    private FirebaseFirestore database;
    private Dialog dialogError;
    private Dialog dialogSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        binding = ActivityModifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Nada mas llegar a esta pantalla, tengo que poner el editText del email de forma que de a entender al usuario que no lo puede modificar
        String email = getIntent().getStringExtra("email");
        binding.editTextEmail.setText(email);

        //Inicializar el custom dialog success
        dialogSuccess = new Dialog(ModifyActivity.this);
        dialogSuccess.setContentView(R.layout.custom_dialog_success);
        dialogSuccess.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogSuccess.setCancelable(true);
        dialogSuccess.getWindow().getAttributes().windowAnimations = R.style.animation;

        //Inicializar el custom dialog error
        dialogError = new Dialog(ModifyActivity.this);
        dialogError.setContentView(R.layout.custom_dialog_error);
        dialogError.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialogError.setCancelable(true);
        dialogError.getWindow().getAttributes().windowAnimations = R.style.animation;

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        binding.buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                String password2 = binding.editTextRepitePassword.getText().toString();
                if ((email.isEmpty()) || (password.isEmpty()) || (password2.isEmpty()) || (nombre.isEmpty())) {
                    Toast.makeText(ModifyActivity.this, R.string.rellenarCampos, Toast.LENGTH_SHORT).show();
                } else if ((!password.equals(password2))) {
                    showAlertPasswords();
                } else if (!esEmailValido(email)) {
                    showAlertEmailError();
                } else {
                    actualizarEnBD(nombre, email, password);
                    actualizarCredenciales(email, password);
                    showAlertSuccess();
                }
            }
        });
    }

    private void actualizarCredenciales(String email, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.updatePassword(password);
    }

    private void actualizarEnBD(String nombre, String email, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("password", password);
        database.collection("usuarios").document(email).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

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
        tvTitulo.setText(R.string.modificarUsuarioExito);
        dialogSuccess.findViewById(R.id.btn_okay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModifyActivity.this, HomeActivity.class);
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