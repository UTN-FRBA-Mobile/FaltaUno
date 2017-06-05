package com.faltauno.faltauno;

import android.content.Context;
import android.support.annotation.StringDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Exequiel on 07/05/2017.
 */

public class OtroAdapter extends RecyclerView.Adapter<OtroAdapter.ViewHolder>{

    private List<Partidos> items;
    private LayoutInflater layoutInflater;
    private String texto;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombrePartido;
        public TextView cancha;
        public TextView fechaHora;
        public TextView creadoPor;
        public TextView jugadores;


        public ViewHolder(View itemView) {
            super(itemView);

            imagen= (ImageView) itemView.findViewById(R.id.iv_imagencancha);
            nombrePartido= (TextView) itemView.findViewById(R.id.tv_nombrepartido);
            cancha= (TextView) itemView.findViewById(R.id.tv_cancha);
            fechaHora= (TextView) itemView.findViewById(R.id.tv_fechahora);
            creadoPor= (TextView) itemView.findViewById(R.id.tv_creadopor);
            jugadores= (TextView) itemView.findViewById(R.id.tv_jugadores);
        }
    }


    public OtroAdapter(Context context, String texto) {
        //layoutInflater = LayoutInflater.from(context);
        this.items=items;
    }

    @Override
    public int getItemCount() {
        //return 10;
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_partidos ,parent,false);

        return new ViewHolder(v);
        //View view = layoutInflater.inflate(R.layout.otro_item, parent, false);
        //return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.textView.setText(texto + " " + String.valueOf(position));
        holder.imagen.setImageResource(items.get(position).getImagen());
        holder.nombrePartido.setText(items.get(position).getnombrePartido());
        holder.cancha.setText(items.get(position).getCancha());
        holder.fechaHora.setText(items.get(position).getFechaHoraPartido());
        holder.creadoPor.setText(items.get(position).getCreadoPor());
        holder.jugadores.setText(items.get(position).getJugadoresFaltantes());
        holder.nombrePartido.setText(items.get(position).getnombrePartido());

    }


}
