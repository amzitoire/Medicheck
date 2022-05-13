package com.android.medicheck.patient;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.medicheck.R;
import com.android.medicheck.models.Consultation;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ConsultationFragment extends Fragment {
    ListView listConsultation;
    ArrayAdapter<Consultation> adapter;

    private Button btnListAll;
   // private ArrayList<String> tabConsultation = new ArrayList<String>();
    private ArrayList<Consultation> tabConsultation = new ArrayList<Consultation>() ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation, container, false);

        // Inflate the layout for this fragment
        listConsultation = view.findViewById(R.id.listConsultation);
        btnListAll =view.findViewById(R.id.list_all);

        tabConsultation = Consultation.getConsultations();

        adapter = new ArrayAdapter<Consultation>(getContext(), android.R.layout.simple_list_item_1, tabConsultation);
        listConsultation.setAdapter(adapter);// chargement des donnees de la liste

        listConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });



        btnListAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

}