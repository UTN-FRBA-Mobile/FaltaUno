package com.faltauno.faltauno;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Chechu on 21/5/2017.
 */

public class DetallePartidoDialogFragment extends DialogFragment {
    private Partido partido;

    public void setArguments(Partido partido, Object estado) {
        Bundle b = new Bundle();
        b.putString("idPartido", partido.id);
        b.putString("titulo",partido.titulo);
        b.putString("fecha",partido.fecha);
        b.putString("hora",partido.hora);
        b.putString("cancha",partido.cancha);
        b.putLong("jugadoresFaltantes",partido.jugadoresFaltantes);
        b.putString("cancha.nombre", partido.canchaRef.getNombre());

        if (estado != null) {
            b.putString("estado", estado.toString());
        } else {
            b.putString("estado", "nada");
        }

        setArguments(b);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //String titulo = getArguments().getString("titulo");
        final String id = getArguments().getString("idPartido");

        String nombreCancha = getArguments().getString("cancha.nombre");
        //String cancha = getArguments().getString("cancha");
        //String fecha = getArguments().getString("fecha");
        //String hora = getArguments().getString("hora");
        //final String host = getArguments().getString("host");
        //String jfaltantes = getArguments().getString("jugadoresFaltantes");

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_det_partido, null);


        TextView horario= (TextView) view.findViewById(R.id.detParHoraPartido);
        horario.setText("Hora: "+getArguments().getString("hora"));

        TextView nombre = (TextView) view.findViewById(R.id.detParNombrePartido);
        nombre.setText(getArguments().getString("titulo"));

        TextView cancha= (TextView) view.findViewById(R.id.detParCancha);
        cancha.setText("Lugar: "+getArguments().getString("cancha.nombre"));

        TextView fechaP = (TextView) view.findViewById(R.id.detParDiaPartido);
        fechaP.setText("Día: "+getArguments().getString("fecha"));

        String lugar = "lugares";
        String queda ="Quedan";
        final long jFalta = getArguments().getLong("jugadoresFaltantes");
        TextView jFaltantes = (TextView) view.findViewById(R.id.detParJFaltantes);
        if (jFalta == 1){
           lugar = "lugar";
           queda = "Queda";
        }
        jFaltantes.setText(queda+" "+String.valueOf(jFalta)+" "+lugar);

        //VERIFICO SI EN EL PARTIDO ELEGIDO SOY HOST O GUEST
        final String estado = getArguments().getString("estado");

        Button botonUnirse = (Button) view.findViewById(R.id.botonUnirse);

        if (estado.equals("host")){

            botonUnirse.setText("Eliminar Partido");

        } else if (estado.equals("guest")) {
            botonUnirse.setText("Bajarse del Partido");
        }

        builder.setView(view);
        Button botonCancel = (Button) view.findViewById(R.id.botonCancelar);
        botonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DetallePartidoDialogFragment.this.getDialog().dismiss();
            }
        });

        botonUnirse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String host = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                if (estado.equals("host")) {
                    borrar(id, host);
                } else if (estado.equals("guest")) {
                    unirse(id, host, jFalta);
                } else {
                    unirse(id, host, jFalta);
                }
                DetallePartidoDialogFragment.this.getDialog().dismiss();
            }
        });
        return builder.create();
    }

    private void unirse(String idPartido, String idUser, long jugadoresFaltantes) {
        //GRABAR JOIN
        DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
        partidosDelUsuario.child(idUser).child(idPartido).setValue("guest");

        //DESCONTAR FALTANTES
        DatabaseReference partidos = FirebaseDatabase.getInstance().getReference().child("partidos");
        partidos.child(idPartido).child("jugadoresFaltantes").setValue(jugadoresFaltantes - 1);
    }

    private void salirse(String idPartido, String idUser, long jugadoresFaltantes) {
        //BORRAR JOIN
        DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
        partidosDelUsuario.child(idUser).child(idPartido).removeValue();

        //INCREMENTAR FALTANTES
        DatabaseReference partidos = FirebaseDatabase.getInstance().getReference().child("partidos");
        partidos.child(idPartido).child("jugadoresFaltantes").setValue(jugadoresFaltantes + 1);
    }

    private void borrar(final String idPartido, String idUser) {
        DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
        //todo: recorrer partidos y borrar

        partidosDelUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
             public void onDataChange(DataSnapshot usuariosSnapshot) {
                      for (DataSnapshot usuarioSnapshot : usuariosSnapshot.getChildren()) {
                                usuarioSnapshot.child(idPartido).getRef().removeValue();
                      }
                  }

                  @Override
                  public void onCancelled(DatabaseError databaseError) {

                  }
              });
        //BORRAR PARTIDO
        DatabaseReference partidos = FirebaseDatabase.getInstance().getReference().child("partidos");
        partidos.child(idPartido).removeValue();
    }
}
