package com.android.medicheck.patient;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;
import com.android.medicheck.models.RendezVous;

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
public class AppointmentFragment extends Fragment {

    ListView listAppointment;
   // private ArrayList<String> tabAppointment = new ArrayList<String>();
    private ArrayList<RendezVous> tabAppointment ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        listAppointment = view.findViewById(R.id.listAppointment);

        tabAppointment = RendezVous.getRendezVous();

        ArrayAdapter<RendezVous> adapter = new ArrayAdapter<RendezVous>(getActivity(), android.R.layout.simple_list_item_1, tabAppointment);
        listAppointment.setAdapter(adapter);// chargement des donnees de la liste


        listAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        /**/    @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        return view;
    }
}
   /* private void getAppointments(){
        ArrayList<String> list = new ArrayList<String>();
        String url = "http://192.168.1.14/android/medicheck/find/appointment.php?id="+MainActivity.id_user;
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
                    JSONArray ja = jo.getJSONArray("rendez_vous");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String date_rv = element.getString("date_rv");
                        String motif_rv = element.getString("motif_rv");
                        String status = element.getString("status");
                        String chaine = "date : "+date_rv+"\nmotif : "+motif_rv+"\nstatut : "+status;
                        list.add(chaine);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tabAppointment.addAll(list);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tabAppointment);
                            listAppointment.setAdapter(adapter);// chargement des donnees de la liste
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

            }
        }*/