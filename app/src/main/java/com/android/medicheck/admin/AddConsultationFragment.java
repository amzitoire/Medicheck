package com.android.medicheck.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.medicheck.R;


public class AddConsultationFragment extends Fragment {
    public static int id_user;
    private EditText txtDateConsultation;
    private EditText txtMotifConsultation;
    private Button btnSaveConsultation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_consultation, container, false);
        btnSaveConsultation = view.findViewById(R.id.btnSaveConsultation);
        txtDateConsultation = view.findViewById(R.id.txtDateConsultation);
        txtMotifConsultation = view.findViewById(R.id.txtMotifConsultation);
        return view;
    }
}