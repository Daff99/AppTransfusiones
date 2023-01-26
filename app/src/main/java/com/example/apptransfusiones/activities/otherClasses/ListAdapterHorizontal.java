package com.example.apptransfusiones.activities.otherClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apptransfusiones.R;

import java.util.ArrayList;

public class ListAdapterHorizontal extends RecyclerView.Adapter<ListAdapterHorizontal.ViewHolder> {

    private ArrayList<ListElementHorizontal> listaElementos;
    //private RecyclerViewInterface interfaz;
    private Context contexto;

    public ListAdapterHorizontal(ArrayList<ListElementHorizontal> lista, Context c) {
        this.listaElementos = lista;
        this.contexto = c;
        //this.interfaz = i;
    }

    @NonNull
    @Override
    public ListAdapterHorizontal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element_horizontal, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterHorizontal.ViewHolder holder, int position) {
        holder.texto.setText(listaElementos.get(position).getTexto());
        holder.imagen.setImageResource(listaElementos.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return listaElementos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagen;
        private TextView texto;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imagen = (ImageView) itemView.findViewById(R.id.imageView3);
            this.texto = itemView.findViewById(R.id.texto);
        }
    }

}
