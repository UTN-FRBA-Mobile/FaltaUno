package com.faltauno.faltauno;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
//import android.support.v4.app.FragmentTransaction;


import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

import android.widget.EditText;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.TimePicker;

//IMPORTS PARA FIREBASE
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NuevoPartidoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NumberPicker numeroJugadores;
    public Spinner spinner;
    public ArrayList<DataSnapshot> canchasList;
    public EditText horaPartido;
    public EditText fechaPartido;
    public EditText nombrePartido;


    public NuevoPartidoFragment() {
        // Required empty public constructor
    }

    public static NuevoPartidoFragment newInstance(String param1, String param2) {
        NuevoPartidoFragment fragment = new NuevoPartidoFragment();
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

        final View vista = inflater.inflate(R.layout.fragment_nuevo_partido, container, false);
        //Toast.makeText(getActivity(), "Arrancando la vistaa", Toast.LENGTH_SHORT).show();

        //Asocio el NumberPicker con el xml
        numeroJugadores = (NumberPicker) vista.findViewById(R.id.numeroJugadores);
        //Le asigno vaor mínimo y máximo
        numeroJugadores.setMinValue(1);
        numeroJugadores.setMaxValue(20);

        //Para nombre del Partido
        nombrePartido = (EditText) vista.findViewById(R.id.nombrePartido);

        // Para datePicker
        final LinearLayout ll = (LinearLayout) vista.findViewById(R.id.l1);
        fechaPartido = (EditText) vista.findViewById(R.id.fechaPartido);
        fechaPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }


        });

        //Para timePicker
        horaPartido = (EditText) vista.findViewById(R.id.horaPartido);
        horaPartido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarReloj();
            }
        });

        //Para Botón
        Button botonCrea = (Button) vista.findViewById(R.id.botonCrear);
        botonCrea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                grabarPartido();
                ((MainActivity)getContext()).cerrarNuevoPartido();
            }

        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("canchas");
              //  .child("c1").child("nombre");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

               ArrayList spinnerList = new ArrayList();
                canchasList = new ArrayList();
               for (DataSnapshot canchaSnapshot: snapshot.getChildren()) {
//                   Cancha cancha = canchaSnapshot.getValue(Cancha.class);
                   canchasList.add(canchaSnapshot);
                    //Toast.makeText(getActivity(), snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                  //Toast.makeText(getActivity(), canchaSnapshot.child("nombre").getValue().toString(), Toast.LENGTH_SHORT).show();
                    spinnerList.add(canchaSnapshot.child("nombre").getValue().toString());
                }
                spinnerList.add("Agregar cancha +");
                //Armo el Spinner de canchas
                spinner = (Spinner) vista.findViewById(R.id.spinner);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(vista.getContext(),android.R.layout.simple_spinner_item, spinnerList);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);

                //Controlo si eligió nueva cancha

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        if (spinner.getSelectedItem().toString()=="Agregar cancha +"){
                            mostrarNuevaCancha();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // Por ahora nadaa
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError error) {
                //Log.e("Chat", "The read failed: " + error.getText());
            }
        });
        return vista;
    }

    private void grabarPartido() {

        //Levanta user
        FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("<id_usuario>");

        //Grabar Campos
        String nombre ="";
        int faltantes = 0;
        String cancha;
        String host = "";
        String fecha = "";
        int img = 0;
        String hora = "";

        nombre = nombrePartido.getText().toString();
        faltantes = numeroJugadores.getValue();
        //Levanto el id de la cancha
        DataSnapshot snapshot = canchasList.get(spinner.getSelectedItemPosition());
        cancha = snapshot.getKey();
        //Levanto el user
        host = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        fecha = fechaPartido.getText().toString();
        hora = horaPartido.getText().toString();
        Long jfaltantes = new Long(faltantes); //TODO: Ver con Vane la clase genérica

        //GRABO PARTIDO NUEVO
        DatabaseReference partidos = FirebaseDatabase.getInstance().getReference().child("partidos");
        String clave = partidos.push().getKey();
        Partido nuevoPartido = new Partido(clave,nombre, jfaltantes,cancha, host,fecha,img,hora);
        partidos.child(clave).setValue(nuevoPartido);

        //GRABO PARTIDOS DEL USUARIO
        DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
        partidosDelUsuario.child(host).child(clave).setValue("host");
        
    }

    public static Fragment newInstance(String texto) {

        NuevoPartidoFragment fragment = new NuevoPartidoFragment();
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

    public void mostrarNuevaCancha(){
        ((MainActivity)getContext()).mostrarNuevaCancha();
    }
}




