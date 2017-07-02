package com.faltauno.faltauno;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class NuevaCanchaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditText direCancha;
    public EditText nombreCancha;

    public NuevaCanchaFragment() {
        // Required empty public constructor
    }

    public static NuevaCanchaFragment newInstance(String param1, String param2) {
        NuevaCanchaFragment fragment = new NuevaCanchaFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View vista = inflater.inflate(R.layout.fragment_crear_cancha, container, false);

        direCancha = (EditText) vista.findViewById(R.id.crearCanchaDire);
        nombreCancha = (EditText) vista.findViewById(R.id.crearCanchaNombre);

        //Para Botón
        Button botonCrea = (Button) vista.findViewById(R.id.botonCrearCancha);
        botonCrea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                grabarCancha();
                ((MainActivity)getContext()).cerrarNuevaCancha();
            }

        });
        return vista;
    }

    public void grabarCancha(){

            //Grabar Campos
            String nombre ="";
            String direccion="";
            int img = 0;

            nombre = nombreCancha.getText().toString();
            direccion = direCancha.getText().toString();

            Cancha nuevaCancha = new Cancha(nombre,direccion,img);

            DatabaseReference canchas = FirebaseDatabase.getInstance().getReference().child("canchas");
            String clave = canchas.push().getKey();
            canchas.child(clave).setValue(nuevaCancha);

        }

    }


