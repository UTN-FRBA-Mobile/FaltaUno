package com.faltauno.faltauno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.faltauno.faltauno.R.id.recyclerViewPartidos;

/**
 * Created by Chechu on 02/07/2017.
 */

public class FragmentMisPartidos extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Partido> partidosList = new ArrayList<>();
//    private List<PartidoXUsuario> partidosXUsuarioList = new ArrayList<>();
    private PartidosFragmentAdapter partidosAdapter;

    private RecyclerView recyclerView;

    public FragmentMisPartidos() {
        // Required empty public constructor
    }

    public static FragmentMisPartidos newInstance(String texto) {
        FragmentMisPartidos fragment = new FragmentMisPartidos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, texto);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        prepareCanchasData();
        return inflater.inflate(R.layout.fragment_prox_partidos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        partidosAdapter = new PartidosFragmentAdapter(getContext(), partidosList);
        recyclerView = (RecyclerView) view.findViewById(recyclerViewPartidos);
        recyclerView.setAdapter(partidosAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prepareMisPartidosData();
    }

    private void prepareMisPartidosData() {
        String usuario;
        int x;
        usuario = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference dbPartXUser = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario").child(usuario);
        dbPartXUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombrePartido;
                String tipo;
                for (DataSnapshot partidoUsuarioSnapshot: dataSnapshot.getChildren()) {
                    nombrePartido = partidoUsuarioSnapshot.getKey().toString();
                    tipo = partidoUsuarioSnapshot.getValue().toString();
                    DatabaseReference refPartido = FirebaseDatabase.getInstance().getReference().child("partidos");
                    refPartido.orderByKey().equalTo(nombrePartido).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String titulo;
                            String cancha;
                            String fecha;
                            String hora;
                            String host;
                            Long jfaltantes;
                            Partido partido;
         //                   for (DataSnapshot partidoSnapshot: dataSnapshot.getChildren()) {
                                DataSnapshot partidoSnapshot = dataSnapshot.getChildren().iterator().next();
                                titulo = partidoSnapshot.child("titulo").getValue().toString();
                                cancha = partidoSnapshot.child("cancha").getValue().toString();
                                fecha = partidoSnapshot.child("fecha").getValue().toString();
                                hora = partidoSnapshot.child("hora").getValue().toString();
                                jfaltantes = partidoSnapshot.child("jugadoresFaltantes").getValue(Long.class);
                                partido = new Partido(titulo, jfaltantes, cancha, "No Jos", fecha, 0, hora);
                                partidosList.add(partido);
                            //}
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}