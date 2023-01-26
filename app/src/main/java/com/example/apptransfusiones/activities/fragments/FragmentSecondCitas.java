package com.example.apptransfusiones.activities.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.apptransfusiones.R;
import com.example.apptransfusiones.databinding.FragmentSecondCitasBinding;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentSecondCitas extends Fragment {

    private FragmentSecondCitasBinding binding;
    private Calendar calendario;
    private final String MY_CHANNEL_ID = "myChannel";
    private final int NOTIFICATION_ID = 0;
    private final CharSequence ID = "MySuperChannel";
    private final String FILE_NAME = "citas.txt";
    private final int REQUEST_CODE = 200;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentSecondCitasBinding.inflate(getLayoutInflater(), container, false);

        calendario = Calendar.getInstance();

        binding.relativeDiaCita.setVisibility(View.GONE);
        binding.relativeHoraCita.setVisibility(View.GONE);
        binding.relativeConfirmarCita.setVisibility(View.GONE);

        binding.btnSeleccionarfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int anyo = calendario.get(Calendar.YEAR);

                DatePickerDialog datepickerdialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int anyoSeleccionado, int mesSeleccionado, int diaSeleccionado) {
                        String diaFormateado, mesFormateado;
                        if (diaSeleccionado < 10) {
                            diaFormateado = "0" + String.valueOf(diaSeleccionado);
                        } else {
                            diaFormateado = String.valueOf(diaSeleccionado);
                        }
                        int otromes = mesSeleccionado + 1;
                        if (otromes < 10) {
                            mesFormateado = "0" + String.valueOf(otromes);
                        } else {
                            mesFormateado = String.valueOf(otromes);
                        }
                        binding.tvdiamesanyo.setText(diaFormateado + "/" + mesFormateado + "/" + anyoSeleccionado);
                    }
                }, anyo, mes, dia);
                datepickerdialog.show();
            }
        });

        binding.btnSeleccionarhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hora = calendario.get(Calendar.HOUR_OF_DAY);
                int minutos = calendario.get(Calendar.MINUTE);
                TimePickerDialog timd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int horaSeleccionada, int minutosSeleccionados) {
                        binding.tvhora.setText(horaSeleccionada + ":" + minutosSeleccionados);
                    }
                }, hora, minutos, false);
                timd.show();
            }
        });

        binding.ivFlechita1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.relativeDiaCita.getVisibility() == View.GONE) {
                    binding.relativeDiaCita.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                    binding.ivFlechita1.setRotation(90.f);
                    binding.relativeDiaCita.setVisibility(View.VISIBLE);
                } else {
                    binding.ivFlechita1.setRotation(0.f);
                    binding.relativeDiaCita.setVisibility(View.GONE);
                }
            }
        });

        binding.ivFlechita2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.relativeHoraCita.getVisibility() == View.GONE) {
                    binding.relativeHoraCita.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                    binding.ivFlechita2.setRotation(90.f);
                    binding.relativeHoraCita.setVisibility(View.VISIBLE);
                } else {
                    binding.ivFlechita2.setRotation(0.f);
                    binding.relativeHoraCita.setVisibility(View.GONE);
                }
            }
        });

        binding.ivFlechita3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.relativeConfirmarCita.getVisibility() == View.GONE) {
                    binding.relativeConfirmarCita.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                    binding.ivFlechita3.setRotation(90.f);
                    binding.relativeConfirmarCita.setVisibility(View.VISIBLE);
                } else {
                    binding.ivFlechita3.setRotation(0.f);
                    binding.relativeConfirmarCita.setVisibility(View.GONE);
                }
            }
        });

        binding.buttonconfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefono = binding.editTextPhone.getText().toString();
                if (telefono.isEmpty()) {
                    Toast.makeText(getContext(), R.string.introduceTelefono, Toast.LENGTH_LONG).show();
                } else if (!esTelefonoValido(telefono)) {
                    Toast.makeText(getContext(), R.string.errorTelefono, Toast.LENGTH_LONG).show();
                } else {
                    binding.tvInformacioncita.setVisibility(View.VISIBLE);
                    binding.buttonconfirmar.setTranslationY(220.f);
                    binding.tvInformacioncita.setText(getText(R.string.elegidocita) + " ");
                    binding.tvInformacioncita.append(binding.tvdiamesanyo.getText() + " ");
                    binding.tvInformacioncita.append(getText(R.string.a_las) + " ");
                    binding.tvInformacioncita.append(binding.tvhora.getText());
                    saveFile();
                    createChannel();
                    createNotification();
                }
            }
        });

        return binding.getRoot();
    }

    private boolean esTelefonoValido(String tlf) {
        Pattern pattern = Pattern.compile("^[0-9]{9}$");
        Matcher matcher = pattern.matcher(tlf);
        return matcher.find();
    }

    private void createChannel() {
        CharSequence name = ID;
        NotificationChannel nc = new NotificationChannel(MY_CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.createNotificationChannel(nc);
    }

    private void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), MY_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo_app);
        builder.setContentTitle(getString(R.string.tituloNotificacion));
        builder.setContentText("Cuerpo de la notificacion");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat nmc = NotificationManagerCompat.from(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            nmc.notify(NOTIFICATION_ID, builder.build());
        } else {
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE);
            createNotification();
        }
    }

    private void saveFile() {
        String fecha = binding.tvdiamesanyo.getText().toString();
        String hora = binding.tvhora.getText().toString();
        try {
            OutputStreamWriter osw = new OutputStreamWriter(getContext().openFileOutput(FILE_NAME, Activity.MODE_APPEND));
            osw.write(fecha);
            osw.write("\n");
            osw.write(hora);
            osw.write("\n");
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}