package com.faltauno.faltauno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.faltauno.faltauno.R.id.recyclerViewCanchas;

/**
 * Created by Chechu on 25/5/2017.
 */

public class FragmentCanchas extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Cancha> canchasList = new ArrayList<>();
    private CanchasFragmentAdapter canchasAdapter;

    private RecyclerView recyclerView;

    public FragmentCanchas() {
        // Required empty public constructor
    }

    public static FragmentCanchas newInstance(String texto) {
        FragmentCanchas fragment = new FragmentCanchas();
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
        return inflater.inflate(R.layout.recycler_view_canchas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        canchasAdapter = new CanchasFragmentAdapter(getContext(), canchasList);
        recyclerView = (RecyclerView) view.findViewById(recyclerViewCanchas);
        recyclerView.setAdapter(canchasAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        prepareCanchasData();
    }

    private void prepareCanchasData() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("canchas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nombre;
                String direccion;
                Cancha cancha;
                for (DataSnapshot canchaSnapshot: dataSnapshot.getChildren()) {
                    //dbList.add(canchaSnapshot);
                    nombre = canchaSnapshot.child("nombre").getValue().toString();
                    direccion = canchaSnapshot.child("direccion").getValue().toString();
                    cancha = new Cancha(nombre, direccion, R.drawable.googleg_standard_color_18);
                    canchasList.add(cancha);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}