package com.example.apptransfusiones.activities.otherClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptransfusiones.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListAdapterCitas extends RecyclerView.Adapter<ListAdapterCitas.ViewHolder> {

    private ArrayList<ListElementCitas> listaElementos;
    private Context contexto;

    public ListAdapterCitas(ArrayList<ListElementCitas> l, Context c) {
        this.listaElementos = l;
        this.contexto = c;
    }

    @NonNull
    @Override
    public ListAdapterCitas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_citas, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterCitas.ViewHolder holder, int position) {
        holder.nombre.setText(listaElementos.get(position).getNombreHospital());
        holder.direccion.setText(listaElementos.get(position).getDireccionHospital());
        holder.ciudad.setText(listaElementos.get(position).getCiudadHospital());
        try {
            InputStreamReader ins = new InputStreamReader(this.contexto.openFileInput("citas.txt"));
            BufferedReader br = new BufferedReader(ins);
            String linea = br.readLine(); //Leo la primera linea de texto de mi fichero
            holder.fecha.setText(linea);
            int lineIndex = 1;
            boolean encontrado = false;
            while (linea != null && !encontrado) {
                if (lineIndex == 2) {
                    encontrado = true;
                    holder.hora.setText(linea);
                    break;
                }
                lineIndex ++;
                linea = br.readLine();
            }
            br.close();
            ins.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (listaElementos.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private TextView direccion;
        private TextView ciudad;
        private TextView fecha;
        private TextView hora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombre = (TextView) itemView.findViewById(R.id.tv_nombre_hospital);
            this.direccion = (TextView) itemView.findViewById(R.id.tv_direccion_hospital);
            this.ciudad = (TextView) itemView.findViewById(R.id.tv_ciudad_hospital);
            this.fecha = (TextView) itemView.findViewById(R.id.tv_fecha_cita);
            this.hora = (TextView) itemView.findViewById(R.id.tv_hora_cita);
        }
    }
}
