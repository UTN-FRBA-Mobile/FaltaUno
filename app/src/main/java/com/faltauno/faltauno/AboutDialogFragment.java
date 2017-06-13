package com.faltauno.faltauno;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import static com.faltauno.faltauno.R.string.button_OK;

/**
 * Created by Chechu on 21/5/2017.
 */

public class AboutDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_about, null))
                // Add action buttons
                .setNeutralButton(R.string.button_OK, new DialogInterface.OnClickListener() {
                    @Override
                    //Cuando haga un click en OK, que llame al metodo dismiss del dialogo
                    public void onClick(DialogInterface dialog, int id) {
                        AboutDialogFragment.this.getDialog().dismiss();
                    }
                });
        return builder.create();
    }
}
