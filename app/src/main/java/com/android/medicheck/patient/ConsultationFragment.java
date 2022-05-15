package com.android.medicheck.patient;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;
import com.android.medicheck.models.Consultation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ConsultationFragment extends Fragment {
    ListView listConsultation;
//    ArrayAdapter<String> adapter;

    private ArrayList<String> tabConsultation = new ArrayList<String>();
//    private ArrayList<Consultation> tabConsultation = new ArrayList<Consultation>() ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultation, container, false);

        // Inflate the layout for this fragment
        listConsultation = view.findViewById(R.id.listConsultation);

//        tabConsultation.addAll(Consultation.findByIdUser(MainActivity.id_user));

//        adapter = new ArrayAdapter<Consultation>(getContext(), android.R.layout.simple_list_item_1, tabConsultation);
//        listConsultation.setAdapter(adapter);// chargement des donnees de la liste
            getConsultations();
//        listConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
        return view;
    }

    private void getConsultations() {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> details = new ArrayList<>();
        String url = "http://"+MainActivity.IPADRESS+"/android/medicheck/find/consultation.php?id="+ MainActivity.id_user;
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
                    JSONArray ja = jo.getJSONArray("consultation");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String date = element.getString("date_consultation");
                        String prenomMedecin = element.getString("prenom");
                        String nomMedecin = element.getString("nom");
                        String specialiteMedecin = element.getString("specialite");
                        String numeroMedecin = element.getString("numero");
                        String motif = element.getString("motif");
                        String status = element.getString("status");
                        String chaine = "date : " + date + "\nmotif : " + motif + "\nstatut : " + status;
                        String medInfos = "medecin : " + prenomMedecin + " "+ nomMedecin + "\nspecialite : "+specialiteMedecin + "\nnumero : "+ numeroMedecin;
                        list.add(chaine);
                        details.add(medInfos);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tabConsultation.addAll(list);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tabConsultation);
                            listConsultation.setAdapter(adapter);// chargement des donnees de la liste
                            listConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    String infos = details.get(i);
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                                    dialog.setIcon(R.mipmap.ic_launcher);
                                    dialog.setTitle("Details");
                                    dialog.setMessage(infos);
                                    dialog.setPositiveButton(getString(R.string.cancel), null);
                                    dialog.show();
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}