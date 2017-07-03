package com.faltauno.faltauno;

/**
 * Created by Chechu on 25/5/2017.
 * Adaptador que muestra la fila de partidos. Acá me traigo los datos del partido
 */
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
public class MisPartidosFragmentAdapter extends RecyclerView.Adapter<MisPartidosFragmentAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;

    private List<MiPartido> partidosList;

    public MisPartidosFragmentAdapter(Context context, List<MiPartido> partidosList) {
        layoutInflater = LayoutInflater.from(context);
        this.partidosList = partidosList;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.mis_partidos_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MiPartido partido = partidosList.get(position);
        holder.partido.setText(partido.getNombrePartido());
        holder.cancha.setText(partido.getCancha());
        holder.fecha.setText(partido.getFechaPartido());
        holder.hora.setText(partido.getHoraPartido());
        holder.rol.setText(partido.getRolPartido());
    }

    @Override
    public int getItemCount() {
        return partidosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView partido, cancha, fecha, hora, rol;

        public ViewHolder(View itemView) {
            super(itemView);
            partido = (TextView) itemView.findViewById(R.id.misPartidosRowNombre);
            cancha = (TextView) itemView.findViewById(R.id.misPartidosCancha);
            fecha = (TextView) itemView.findViewById(R.id.misPartidosFecha);
            hora = (TextView) itemView.findViewById(R.id.misPartidosHora);
            rol = (TextView) itemView.findViewById(R.id.misPartidosRol);
        }
    }
}
