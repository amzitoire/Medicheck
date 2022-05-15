package com.android.medicheck.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MedicalFileFragment extends Fragment {

    private TextView tvMedicalFile;
    private String  file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_medical_file, container, false);
        tvMedicalFile = view.findViewById(R.id.tvMedicalFile);
        getFile();
        return view;
    }

    private void getFile(){
        String url = "http://"+ MainActivity.IPADRESS+ "/android/medicheck/find/medicalFile.php?id="+MainActivity.id_user;
        file= "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("dossier");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String prenom = element.getString("prenom");
                        String nom = element.getString("nom");
                        String sexe = element.getString("sexe");
                        String date_naissance = element.getString("date_naissance");
                        String poids = element.getString("poids");
                        file = "Prénom : "+ prenom + "\nNom : "+nom+"\nSexe : "+sexe+"\nDate de naissance : "+date_naissance+"\nPoids : "+poids;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvMedicalFile.setText(file);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    }
