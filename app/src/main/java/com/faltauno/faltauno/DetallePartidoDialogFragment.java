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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Chechu on 21/5/2017.
 */

public class DetallePartidoDialogFragment extends DialogFragment {
    private Partido partido;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //String titulo = getArguments().getString("titulo");
        final String id = getArguments().getString("idPartido");
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

        builder.setView(view);
        Button botonCancel = (Button) view.findViewById(R.id.botonCancelar);
        botonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DetallePartidoDialogFragment.this.getDialog().dismiss();
            }
        });
        Button botonUnirse = (Button) view.findViewById(R.id.botonUnirse);
        botonUnirse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String host = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                //GRABAR JOIN
                DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
                partidosDelUsuario.child(host).child(id).setValue("guest");
                //DESCONTAR FALTANTES

                DatabaseReference partidos = FirebaseDatabase.getInstance().getReference().child("partidos");
                //String clave = partidos.push().getKey();
                //Partido nuevoPartido = new Partido(clave,nombre, jfaltantes,cancha, host,fecha,img,hora);

                partidos.child(id).child("jugadoresFaltantes").setValue(jFalta-1);
                DetallePartidoDialogFragment.this.getDialog().dismiss();



            }
        });
        return builder.create();
    }
}
