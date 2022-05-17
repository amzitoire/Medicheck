package com.android.medicheck.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

    private TextView tvPrenom;
    private TextView tvNom;
    private TextView tvsexe;
    private TextView tvdate;
    private TextView tvpoids;
    private TextView tvMedicalFile;

    private String  file;
    private String  nom;
    private String  prenom;
    private String  sexe;
    private String  date;
    private String  poids;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_medical_file, container, false);
      //  tvMedicalFile = view.findViewById(R.id.tvMedicalFile);
        tvPrenom =view.findViewById(R.id.tvPrenom);
        tvNom =view.findViewById(R.id.tvNom);
        tvdate =view.findViewById(R.id.tvdate);
        tvsexe =view.findViewById(R.id.tvSexe);
        tvpoids =view.findViewById(R.id.tvpoids);
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
                        String prenomD = element.getString("prenom");
                        String nomD = element.getString("nom");
                        String sexeD = element.getString("sexe");
                        String date_naissanceD = element.getString("date_naissance");
                        String poidsD = element.getString("poids");
                        prenom = "Prenom : "+prenomD ;
                        nom = "Nom : "+nomD;
                        date= "Date de naissance :"+date_naissanceD;
                        sexe = "Sexe :"+sexeD;
                        poids = "Poids :"+poidsD;
                       // file = "Prénom : "+ prenom + "\nNom : "+nom+"\nSexe : "+sexe+"\nDate de naissance : "+date_naissance+"\nPoids : "+poids;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            tvPrenom.setText(prenom);
                            tvNom.setText(nom);
                            tvdate.setText(date);
                            tvsexe.setText(sexe);
                            tvpoids.setText(poids);
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
