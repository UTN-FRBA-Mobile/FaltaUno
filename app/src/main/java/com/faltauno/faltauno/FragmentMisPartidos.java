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
        usuario = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        final DatabaseReference refPartXUser = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario").child(usuario);
        refPartXUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot partidosUsuarioSnapshot) {
                partidosList.clear();
                DatabaseReference refPartido = FirebaseDatabase.getInstance().getReference().child("partidos");
                refPartido.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot partidosSnapshot) {
                        DatabaseReference refCanchas = FirebaseDatabase.getInstance().getReference().child("canchas");
                        refCanchas.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot canchasSnapshot) {
                                for (DataSnapshot partidoUsuarioSnapshot : partidosUsuarioSnapshot.getChildren()) {
                                    String idPartido = partidoUsuarioSnapshot.getKey();
                                    String rol = partidoUsuarioSnapshot.getValue().toString();
                                    for (DataSnapshot partidoSnapshot : partidosSnapshot.getChildren()) {
                                        Partido partido = partidoSnapshot.getValue(Partido.class);
                                        //Levanto el id del partido
                                        partido.id = partidoSnapshot.getKey();
                                        if (partido.id == idPartido) {
                                            continue;
                                        }
                                        //Levanto datos de la cancha
                                        Cancha canchaRef = canchasSnapshot.child(partido.cancha).getValue(Cancha.class);
                                        partido.canchaRef = canchaRef;

                                        partidosList.add(partido);
                                    }
                                    partidosAdapter.setPartidosList(partidosList);

                                }
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}