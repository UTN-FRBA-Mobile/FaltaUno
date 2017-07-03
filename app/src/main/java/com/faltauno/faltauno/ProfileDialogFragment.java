package com.faltauno.faltauno;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Chechu on 21/5/2017.
 */

public class ProfileDialogFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view  = inflater.inflate(R.layout.dialog_profile,null);

        final EditText tNombrePerfil = (EditText) view.findViewById(R.id.textNombrePerfil);
        final EditText tUsuario = (EditText) view.findViewById(R.id.tUsuario);

        ImageButton buttonEditar = (ImageButton) view.findViewById(R.id.buttonNombreUsuario);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tNombrePerfil.setEnabled(true);
                tUsuario.setEnabled(true);
//                tNombrePerfil.setFocusableInTouchMode(true);
//                tUsuario.setFocusableInTouchMode(true);
            }
        });
        // agarrar datos de la sharedpref y mostrarlos!!
        final SharedPreferences pref = this.getActivity().getSharedPreferences("USUARIO_DATOS", MODE_PRIVATE);

        tNombrePerfil.setText(pref.getString("nombre",null));
        tUsuario.setText(pref.getString("usuario",null));

        builder.setView(view)
                // Add action buttons
                .setNeutralButton(R.string.button_OK, new DialogInterface.OnClickListener() {
                    @Override
                    //Cuando haga un click en OK, que llame al metodo dismiss del dialogo
                    public void onClick(DialogInterface dialog, int id) {
                        //aca guardar datos en la db
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios/"+user.getUid());
                        Usuario usuario = new Usuario(tNombrePerfil.getText().toString(),tUsuario.getText().toString());
                        ref.setValue(usuario);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("nombre",usuario.getNombre());
                        editor.putString("usuario",usuario.getUsuario());
                        editor.commit();
                        ProfileDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();
    }
}
