package com.faltauno.faltauno;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
//import android.support.v4.app.FragmentTransaction;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.app.DialogFragment;
import java.util.Calendar;

import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.EditText;
import android.app.Activity;
import android.view.View;
import android.app.DialogFragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//IMPORTS PARA FIREBASE
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class NuevoPartido extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NumberPicker np;
    public Spinner spinner;
    
    public NuevoPartido() {
        // Required empty public constructor
    }

    public static NuevoPartido newInstance(String param1, String param2) {
        NuevoPartido fragment = new NuevoPartido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista = inflater.inflate(R.layout.fragment_nuevo_partido, container, false);
        //Toast.makeText(getActivity(), "Arrancando la vistaa", Toast.LENGTH_SHORT).show();

        //Asocio el NumberPicker con el xml
        np = (NumberPicker) vista.findViewById(R.id.numeroJugadores);
        //Le asigno vaor mínimo y máximo
        np.setMinValue(1);
        np.setMaxValue(20);

        //Armo el Spinner de canchas
        spinner = (Spinner) vista.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(vista.getContext(),
                R.array.canchas_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // Para datePicker
        final LinearLayout ll = (LinearLayout) vista.findViewById(R.id.l1);
        EditText fechaPartido = (EditText) vista.findViewById(R.id.fechaPartido);
        fechaPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }


        });

        //Para timePicker
        EditText horaPartido = (EditText) vista.findViewById(R.id.horaPartido);
        horaPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarReloj();
            }


        });

        //PRUEBA FIREBASE
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("canchas")
                .child("c1").child("nombre");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                //PARA VER SI LEVANTO DE LA BD, LO QUE HAGO ES PISAR EL TÍTULO
                //DE NUEVO PARTIDO, CON EL NOMBRE DE LA PRIMER CANCHA QUE CARGUÉ EN MI BASE
                
                TextView prueba = (TextView) vista.findViewById(R.id.textView2);
                String valor = snapshot.getValue().toString();
                prueba.setText(valor);


               // prueba.setText(snapshot.getValue().toString());

               /* for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
                    Cancha msg = msgSnapshot.getValue(Cancha.class);
                    Toast.makeText(getActivity(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    //Log.i("Chat", msg.getName()+": ");//+msg.getText());

                }*/
            }
            @Override
            public void onCancelled(DatabaseError error) {
                //Log.e("Chat", "The read failed: " + error.getText());
            }
        });

        return vista;

    }
    public static Fragment newInstance(String texto) {

        NuevoPartido fragment = new NuevoPartido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, texto);
        fragment.setArguments(args);
        return fragment;
    }

    public void mostrarCalendario() {

        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                EditText fecha = (EditText) getActivity().findViewById(R.id.fechaPartido);
                fecha.setText( selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
            }
        }, year, month, day);
        datePicker.setTitle("Ingrese día");
        datePicker.show();
    }
    public void mostrarReloj() {

        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                EditText horario = (EditText) getActivity().findViewById(R.id.horaPartido);
                horario.setText( hourOfDay + ":" + minute);

            }

        },hora,minutos,false);
        timePicker.setTitle("Ingrese Horario");
        timePicker.show();
    }
}




