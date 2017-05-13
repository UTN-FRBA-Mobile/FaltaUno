package com.faltauno.faltauno;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.faltauno.faltauno.dummy.DummyContent;
import com.faltauno.faltauno.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * Created by Exequiel 07/05/17
 */
public class FragmentProxPartidos extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_PARAM1 = "param1";
    // TODO: Customize parameters
    private String mParam1;

    private RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentProxPartidos() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentProxPartidos newInstance(String texto) {
        FragmentProxPartidos fragment = new FragmentProxPartidos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, texto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prox_partidos, container, false);
    }

public void onViewCreated (View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    recyclerView.setAdapter(new OtroAdapter(getContext(),mParam1));
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
}

}