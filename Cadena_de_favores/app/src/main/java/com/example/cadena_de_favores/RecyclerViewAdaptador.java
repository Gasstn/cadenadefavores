package com.example.cadena_de_favores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo,nombre,descripcion,fecha;
        ImageView fotofavor;

        public ViewHolder(View itemView) {
            super(itemView);
            fecha=(TextView)itemView.findViewById(R.id.tvfecha);
            nombre=(TextView)itemView.findViewById(R.id.tvnombre);
            titulo=(TextView)itemView.findViewById(R.id.tvtitulo);
            descripcion=(TextView)itemView.findViewById(R.id.tvdescipcion);


        }
    }

    public List<Modelofavor> favorLista;

    public RecyclerViewAdaptador(List<Modelofavor> favorLista) {
        this.favorLista = favorLista;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favor,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.fecha.setText(favorLista.get(position).getFecha());
        holder.nombre.setText(favorLista.get(position).getNombre());
        holder.titulo.setText(favorLista.get(position).getTitulo());
        holder.descripcion.setText(favorLista.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return favorLista.size();
    }
}

