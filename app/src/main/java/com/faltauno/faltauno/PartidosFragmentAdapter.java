package com.faltauno.faltauno;

/**
 * Created by Chechu on 25/5/2017.
 * Adaptador que muestra la fila de partidos. Acá me traigo los datos del partido
 */
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class PartidosFragmentAdapter extends RecyclerView.Adapter<PartidosFragmentAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;

    private List<Partido> partidosList;

    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(Partido partido);
    }

    public PartidosFragmentAdapter(Context context, List<Partido> partidosList) {
        layoutInflater = LayoutInflater.from(context);
        this.partidosList = partidosList;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
    public void setPartidosList(List<Partido> partidosList) {
        this.partidosList = partidosList;
        notifyDataSetChanged();
        //TODO: refrescar ReyclerView
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.partido_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Partido partido = partidosList.get(position);
        holder.partido.setText(partido.titulo);
        holder.cancha.setText(partido.cancha);
        holder.fecha.setText(partido.fecha);
        holder.hora.setText(partido.hora);
        holder.jfaltantes.setText(Long.toString(partido.jugadoresFaltantes));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(partido);
            }
        });
    }

    @Override
    public int getItemCount() {
        return partidosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView partido, cancha, fecha, hora, creadopor, jfaltantes;
        //TextView titulo, ubicacion, barrio;

        public ViewHolder(View itemView) {
            super(itemView);
            partido = (TextView) itemView.findViewById(R.id.partidoRowNombre);
            cancha = (TextView) itemView.findViewById(R.id.partidoRowCancha);
            fecha = (TextView) itemView.findViewById(R.id.partidoRowFecha);
            hora = (TextView) itemView.findViewById(R.id.partidoRowHora);
            creadopor = (TextView) itemView.findViewById(R.id.partidoRowCreadopor);
            jfaltantes = (TextView) itemView.findViewById(R.id.partidoRowJugadoresF);
        }
    }
}
