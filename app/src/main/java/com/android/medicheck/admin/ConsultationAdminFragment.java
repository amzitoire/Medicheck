package com.android.medicheck.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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


public class ConsultationAdminFragment extends Fragment {

    private ListView listConsultation;
    private Button btnAddConsultation;
    private ArrayList<Consultation> tabConsultation = new ArrayList<Consultation>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultation_admin, container, false);
        listConsultation = view.findViewById(R.id.listConsultationAdmin);
        getConsultations();
        listConsultation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Consultation cons = (Consultation) listConsultation.getItemAtPosition(i);
                String details = cons.toString();
                // selected item

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setIcon(R.mipmap.ic_logo_app_round);
                dialog.setTitle(R.string.consultation);
                dialog.setMessage(details);
                dialog.setNeutralButton(getString(R.string.cancel),null);
                dialog.setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.setPositiveButton("Gerer consultation", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                        dialog.setIcon(R.mipmap.ic_logo_app_round);
                        dialog.setTitle(R.string.consultation);
                        dialog.setMessage(details);
                        dialog.setNeutralButton(getString(R.string.cancel),null);
                        dialog.setNegativeButton(getString(R.string.alter), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog.setPositiveButton(getString(R.string.add_appointment), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AddAppointmentFragment.id_consultation = cons.getId_consultation();
                                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_admin, new AddAppointmentFragment()).addToBackStack(null).commit();

                            }
                        });
                        dialog.show();
                    }
                });
                dialog.show();
            }
        });
        return view;
    }

    private void getConsultations(){
        ArrayList<Consultation> list = new ArrayList<Consultation>();
        String url = "http://"+MainActivity.IPADRESS+"/android/medicheck/list/consultation.php";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
                        String date_rv = element.getString("date_consultation");
                        String motif_rv = element.getString("motif");
                        String status = element.getString("status");
                        int id_consultation = element.getInt("id_consultation");

                        Consultation consultation = new Consultation();
                        consultation.setId_consultation(id_consultation);
                        consultation.setDate_consultation(date_rv);
                        consultation.setMotif_consultation(motif_rv);
                        consultation.setStatuts(status);

                       // String chaine = "date : "+date_rv+"\nmotif : "+motif_rv+"\nstatut : "+status;
                        list.add(consultation);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tabConsultation.addAll(list);
                            ArrayAdapter<Consultation> adapter = new ArrayAdapter<Consultation>(getActivity(), android.R.layout.simple_list_item_1, tabConsultation);
                            listConsultation.setAdapter(adapter);// chargement des donnees de la liste
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