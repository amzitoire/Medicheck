package com.android.medicheck.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;
import com.android.medicheck.models.ExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PatientAdminFragment extends Fragment {
    private ExpandableListView listPatient;
    private ExpandableListAdapter listAdapter;
    private ArrayList<String> tabPatient = new ArrayList<String>();
    private ArrayList<Integer> tabIdUser = new ArrayList<Integer>();
    private List<String> options = new ArrayList<String>() ;
    private HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        options.add(getString(R.string.add_appointment));
        options.add(getString(R.string.add_consultation));
        options.add(getString(R.string.add_medical_file));
        options.add(getString(R.string.delete));
        View view = inflater.inflate(R.layout.fragment_patient_admin, container, false);
        listPatient = view.findViewById(R.id.listPatient);
        getUsers();
        listPatient.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int id_user = tabIdUser.get(groupPosition);
                if(childPosition == 0){
                    AddAppointmentFragment.id_user = id_user;
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_admin, new AddAppointmentFragment()).addToBackStack(null).commit();
                }
                else if(childPosition == 1){
                    AddConsultationFragment.id_user = id_user;
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_admin, new AddConsultationFragment()).addToBackStack(null).commit();
                }
                else if(childPosition == 2){
                    AddConsultationFragment.id_user = id_user;
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_admin, new AddMedicalFileFragment()).addToBackStack(null).commit();
                }
                return false;
            }
        });

        return view;
    }

    private void getUsers(){
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<Integer> ids = new ArrayList<Integer>();
        String url = "http://"+ MainActivity.IPADRESS+"/android/medicheck/list/patient.php";
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
                    JSONArray ja = jo.getJSONArray("patient");
                    for (int i = 0; i < ja.length(); i++){
                        JSONObject element = ja.getJSONObject(i);
                        int id_user = element.getInt("id_user");
                        String prenom = element.getString("prenom");
                        String nom = element.getString("nom");
                        String login = element.getString("login");
                        String chaine = "Id : "+id_user+"\nPrenom : "+prenom+"\nNom : "+nom+"\nLogin : "+login;
                        listDataChild.put(chaine,options);
                        list.add(chaine);
                        ids.add(id_user);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tabPatient.addAll(list);
                            tabIdUser.addAll(ids);
                            listAdapter = new ExpandableListAdapter(getActivity(),tabPatient,listDataChild);
                            listPatient.setAdapter(listAdapter);// chargement des donnees de la liste
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