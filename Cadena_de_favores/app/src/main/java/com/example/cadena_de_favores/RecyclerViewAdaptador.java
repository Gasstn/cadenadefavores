package com.example.cadena_de_favores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> implements View.OnClickListener {

    public int x=1;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titulo,nombre,descripcion,fecha,nro_tel;
        private CardView cvItem;
        private Button btn_contacto;
        ImageView fotofavor,imagen;

        @SuppressLint("WrongConstant")
        public ViewHolder(View itemView) {
            super(itemView);
            cvItem=(CardView)itemView.findViewById(R.id.cvItem);
            fecha=(TextView)itemView.findViewById(R.id.tvfecha);
            nombre=(TextView)itemView.findViewById(R.id.tvnombre);
            titulo=(TextView)itemView.findViewById(R.id.tvtitulo);
            descripcion=(TextView)itemView.findViewById(R.id.tvdescipcion);
            nro_tel=(TextView)itemView.findViewById(R.id.tvNroTel);
            imagen=(ImageView)itemView.findViewById(R.id.imageViewSeparator);
            btn_contacto=(Button)itemView.findViewById(R.id.btn_contacto);
            cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (btn_contacto.getVisibility() == View.VISIBLE){
                        btn_contacto.setVisibility(View.INVISIBLE);
                    } else {
                        btn_contacto.setVisibility(View.VISIBLE);
                    }

                }
            });

            btn_contacto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = (String) nro_tel.getText();
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel: " + tel));
                    v.getContext().startActivity(i);
                }
            });
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

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(x == 1 ) {
            holder.imagen.setImageResource(R.drawable.linea1);
            x++;
        }else if(x == 2){
            holder.imagen.setImageResource(R.drawable.linea2);
            x++;
        }else if(x == 3){
            holder.imagen.setImageResource(R.drawable.linea3);
            x++;
        }else if(x == 4){
            holder.imagen.setImageResource(R.drawable.linea4);
            x=1;
        }

        holder.fecha.setText(favorLista.get(position).getFecha());
        holder.nombre.setText(favorLista.get(position).getNombre());
        holder.titulo.setText(favorLista.get(position).getTitulo());
        holder.descripcion.setText(favorLista.get(position).getDescripcion());
        holder.nro_tel.setText(favorLista.get(position).getNro_tel());
    }

    @Override
    public int getItemCount() {
        return favorLista.size();
    }
}



