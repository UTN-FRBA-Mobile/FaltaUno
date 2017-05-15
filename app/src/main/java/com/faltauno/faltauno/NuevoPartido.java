package com.faltauno.faltauno;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.ArrayAdapter;


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
        View vista =inflater.inflate(R.layout.fragment_nuevo_partido, container, false);
        //Asocio el NumberPicker con el xml
        np = (NumberPicker) vista.findViewById (R.id.numeroJugadores);
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
        return vista;

    }

    // TODO: Rename method, update argument and hook method into UI event
/*
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/

   // @Override
 /*   public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

  /*  @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    public static Fragment newInstance(String texto) {

        NuevoPartido fragment = new NuevoPartido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, texto);
        fragment.setArguments(args);
        return fragment;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
/*    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/


}