package com.faltauno.faltauno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.faltauno.faltauno.R.id.recyclerViewPartidos;

/**
 * Created by Exequiel on 25/5/2017.
 */

public class FragmentPartidos extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Partido> partidosList = new ArrayList<>();
    private PartidosFragmentAdapter partidosAdapter;

    private RecyclerView recyclerView;

    public FragmentPartidos() {
        // Required empty public constructor
    }

    public static FragmentPartidos newInstance(String texto) {
        FragmentPartidos fragment = new FragmentPartidos();
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
        preparePartidosData();

        //
        partidosAdapter.setOnClickListener(new PartidosFragmentAdapter.OnClickListener() {
            @Override
            public void onClick(Partido partido) {
                //ARMO DETALLE PARTIDO
                FragmentManager fm = getFragmentManager();
                DetallePartidoDialogFragment detalleDialogFragment = new DetallePartidoDialogFragment();
                Bundle b = new Bundle();
                b.putString("idPartido", partido.id);
                b.putString("titulo",partido.titulo);
                b.putString("fecha",partido.fecha);
                b.putString("hora",partido.hora);
                b.putString("cancha",partido.cancha);
                b.putLong("jugadoresFaltantes",partido.jugadoresFaltantes);
                b.putString("cancha.nombre", partido.canchaRef.getNombre());

                detalleDialogFragment.setArguments(b);
                detalleDialogFragment.show(fm, "detalle");
            }
        });

        //Agrego código para abrir fragment NuevoPartidoFragment
        FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.nuevoPartido);
        fab1.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                mostrarNuevoPartido();
            }
        });
    }

    private void mostrarNuevoPartido() {
        ((MainActivity)getContext()).mostrarNuevoPartido();
    }

    private void preparePartidosData() {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("partidos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot partidosSnapshot) {
                partidosList.clear();
                DatabaseReference refCanchas = FirebaseDatabase.getInstance().getReference().child("canchas");
                refCanchas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot canchasSnapshot) {
                        for (DataSnapshot partidoSnapshot: partidosSnapshot.getChildren()) {
                            Partido partido = partidoSnapshot.getValue(Partido.class);
                            if (partido.jugadoresFaltantes==0) {
                                continue;
                            }
                            //Levanto el id del partido
                            partido.id = partidoSnapshot.getKey();

                            //Levanto datos de la cancha
                            Cancha canchaRef = canchasSnapshot.child(partido.cancha).getValue(Cancha.class);
                            partido.canchaRef = canchaRef;

                            partidosList.add(partido);
                        }
                        partidosAdapter.setPartidosList(partidosList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}