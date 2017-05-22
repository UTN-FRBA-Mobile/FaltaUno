package com.faltauno.faltauno;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
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
import android.widget.TimePicker;


import static com.faltauno.faltauno.R.id.fechaPartido;
import static com.faltauno.faltauno.R.id.numeroJugadores;

public class NuevoPartido extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public NumberPicker np;
    public Spinner spinner;





    //private OnFragmentInteractionListener mListener;

    public NuevoPartido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NuevoPartido.
     */

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


//        String[] nums = new String[20];
//        for(int i=0; i<nums.length; i++)
//            nums[i] = Integer.toString(i);
//
//        np.setMinValue(1);
//        np.setMaxValue(20);
//        np.setWrapSelectorWheel(false);
//        np.setDisplayedValues(nums);
//        np.setValue(1);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_nuevo_partido, container, false);
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


      /* //To show current date in the datepicker
        Calendar currentDate = Calendar.getInstance();
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // TODO Auto-generated method stub
                    *//*      Your code   to get date and time    *//*
            }
        }, year, month, day);
        datePicker.setTitle("Select date");
        datePicker.show();*/
    }
}




