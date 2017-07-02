package com.faltauno.faltauno;

/**
 * Created by Chechu on 25/5/2017.
 * Adaptador que muestra la fila de canchas. Acá me traigo los datos de las canchas
 */
//TODO levantar los datos de la BD

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CanchasFragmentAdapter extends RecyclerView.Adapter<CanchasFragmentAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;

    private List<Cancha> canchasList;

    public CanchasFragmentAdapter(Context context, List<Cancha> canchasList) {
        layoutInflater = LayoutInflater.from(context);
        this.canchasList = canchasList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cancha_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cancha cancha = canchasList.get(position);
        holder.titulo.setText(cancha.getNombre());
        holder.ubicacion.setText(cancha.getDireccion());
        //holder.barrio.setText(cancha.getBarrio());
    }

    @Override
    public int getItemCount() {
        return canchasList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, ubicacion;
        //TextView titulo, ubicacion, barrio;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.canchaRowNombre);
            ubicacion = (TextView) itemView.findViewById(R.id.canchaRowUbicacion);
            //barrio = (TextView) itemView.findViewById(R.id.canchaRowBarrio);
        }
    }
}
