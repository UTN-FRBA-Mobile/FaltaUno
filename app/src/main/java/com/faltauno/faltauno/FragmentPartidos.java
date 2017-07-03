package com.faltauno.faltauno;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.database.DatabaseUtilsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.faltauno.faltauno.R.id.recyclerViewPartidos;

/**
 * Created by Exequiel on 25/5/2017.
 */

public class FragmentPartidos extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Partido> partidosList = new ArrayList<>();
    private PartidosFragmentAdapter partidosAdapter;
    private DataSnapshot misPartidos;
    private Geocoder geocoder;


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
        geocoder = new Geocoder(this.getContext());

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
                // OBTENGO SI ES HOST O GUEST
                Object estado = misPartidos.child(partido.id).getValue();

                //ARMO DETALLE PARTIDO
                FragmentManager fm = getFragmentManager();
                DetallePartidoDialogFragment detalleDialogFragment = new DetallePartidoDialogFragment();
                detalleDialogFragment.setArguments(partido, estado);
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        DatabaseReference partidosDelUsuario = FirebaseDatabase.getInstance().getReference().child("partidosDelUsuario");
        partidosDelUsuario.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                misPartidos = dataSnapshot;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("partidos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot partidosSnapshot) {
                partidosList.clear();
                DatabaseReference refCanchas = FirebaseDatabase.getInstance().getReference().child("canchas");
                refCanchas.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot canchasSnapshot) {
                        for (DataSnapshot partidoSnapshot : partidosSnapshot.getChildren()) {
                            Partido partido = partidoSnapshot.getValue(Partido.class);
                            if (partido.jugadoresFaltantes == 0) {
                                continue;
                            }
                            //Levanto el id del partido
                            partido.id = partidoSnapshot.getKey();

                            //Levanto datos de la cancha
                            Cancha canchaRef = canchasSnapshot.child(partido.cancha).getValue(Cancha.class);
                            partido.canchaRef = canchaRef;

                            //obtengo la distancia de la cancha donde se juega el partido a la del usuario
                            try {
                                partido.setDistancia(calculateDistance(canchaRef.getDireccion()));
                            } catch (IOException e) {
                                e.printStackTrace();
                                partido.setDistancia(new Float(99999.999));
                            }
                            partidosList.add(partido);
                        }

                        sortList(partidosList); //ordenar la lista por distancia antes de mostrarla en pantalla
                        partidosAdapter.setPartidosList(partidosList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    //calcula la distancia entre la cancha y el usuario
    private float calculateDistance(String dir) throws IOException {
        //hace la busqueda de la locacion por la direccion de la cancha, devuelve una lista de 1 solo elemento
        List<Address> list = geocoder.getFromLocationName(dir,1);
        Address add = list.get(0);

        Location canchaLocation = new Location("Cancha");
        canchaLocation.setLatitude(add.getLatitude());
        canchaLocation.setLongitude(add.getLongitude());

        Location userLocation = new Location("User Location");
        userLocation.setLatitude(((MainActivity)getContext()).getUserLatitude());
        userLocation.setLongitude(((MainActivity)getContext()).getUserLongitude());
        Float dist = canchaLocation.distanceTo(userLocation);

        System.out.println("la distancia entre la cancha " + dir + " y el usuario que esta en " + userLocation.toString()
                + " es " + dist.toString());

        return canchaLocation.distanceTo(userLocation);
    }

    //ordena la lista de canchas por menor distancia al usuario
    private static void sortList(List<Partido> list) {
        // Sorting
        Collections.sort(list, new Comparator<Partido>() {

            public int compare(Partido p1, Partido p2) {
                if (p1.distancia > p2.distancia)
                    return  1;
                else if (p1.distancia == p2.distancia)
                    return 0;
                else return -1;
            }
        });
    }
}