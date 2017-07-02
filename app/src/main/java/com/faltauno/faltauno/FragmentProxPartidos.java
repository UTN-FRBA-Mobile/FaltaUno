package com.faltauno.faltauno;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Exequiel 07/05/17
 */
public class FragmentProxPartidos extends Fragment {
    ListView listaPartidos;
    ArrayAdapter<String> listaPartidosAdapter;

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

    FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.nuevoPartido);
    //Agrego código para abrir fragment NuevoPartido
    fab1.setOnClickListener(new View.OnClickListener(){
        public void onClick (View v){
            mostrarNuevoPartido();
        }
    });
    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    recyclerView.setAdapter(new ListaDePartidosAdapter(getContext(),mParam1));
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

}

private void mostrarNuevoPartido() {
    ((MainActivity)getContext()).mostrarNuevoPartido();
}

}
