package com.android.medicheck.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.medicheck.R;


public class AddAppointmentFragment extends Fragment {
    private EditText txtDate;
    private EditText txtMotif;
    private Button btnSaveAppointment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        btnSaveAppointment = view.findViewById(R.id.btnSaveAppointment);
//        txtDate = view.findViewById(R.id.txtDate);
        txtMotif = view.findViewById(R.id.txtMotif);
        return view;
    }
}