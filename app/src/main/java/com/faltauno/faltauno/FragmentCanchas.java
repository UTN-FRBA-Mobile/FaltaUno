package com.faltauno.faltauno;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.faltauno.faltauno.R.id.recyclerView;
import static com.faltauno.faltauno.R.id.recyclerViewCanchas;

/**
 * Created by Chechu on 25/5/2017.
 */

public class FragmentCanchas extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Canchas> canchasList = new ArrayList<>();
    private CanchasFragmentAdapter canchasAdapter;

    private String mParam1;

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
        Canchas cancha = new Canchas("Futbol Caballito", "Rivadavia 2000", "Caballito", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Almagro", "Rivadavia 3000", "Almagro", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Caballito 2", "Riglos 950", "Caballito", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Lugano I", "Escalada y Autopista", "Lugano" , R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Lugano II", "Mozart 2300", "Lugano", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Caballito 3", "Riglos 950", "Caballito", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Lugano III", "Escalada y Autopista", "Lugano" , R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        cancha = new Canchas("Futbol Lugano IV", "Mozart 2300", "Lugano", R.drawable.messenger_bubble_large_blue);
        canchasList.add(cancha);

        canchasAdapter.notifyDataSetChanged();
    }
}

