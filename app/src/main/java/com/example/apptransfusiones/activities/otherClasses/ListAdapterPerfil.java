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

public class ListAdapterPerfil extends RecyclerView.Adapter<ListAdapterPerfil.ViewHolder> {

    private ArrayList<ListElement> listaElementos;
    private static RecyclerViewInterface interfaz;
    private Context contexto;

    public ListAdapterPerfil(ArrayList<ListElement> lista, Context c, RecyclerViewInterface i) {
        this.listaElementos = lista;
        this.contexto = c;
        this.interfaz = i;
    }

    @NonNull
    @Override
    public ListAdapterPerfil.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterPerfil.ViewHolder holder, int position) {
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
            this.imagen = (ImageView) itemView.findViewById(R.id.image_view_medalla);
            this.texto = itemView.findViewById(R.id.textView_medalla);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (interfaz != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            interfaz.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
