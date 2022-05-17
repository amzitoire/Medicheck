package com.android.medicheck.admin;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;
import com.android.medicheck.models.Medecin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddConsultationFragment extends Fragment {
    public static int id_user;
    public static int id_medecin;

    ArrayList<Medecin> items = new ArrayList<Medecin>() ;

    private EditText txtDateConsultation;
    private EditText txtMotifConsultation;
    private Button btnSaveConsultation;
    private TextView tvDate;
    private Spinner dropdown;

    private String date,motif;
    private Date dateFormat ;


    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getMedecin();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_consultation, container, false);
        btnSaveConsultation = view.findViewById(R.id.btnSaveConsultation);
        txtMotifConsultation = view.findViewById(R.id.txtMotifConsultation);

        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                tvDate.setText(date);
            }
        };

        //get the spinner from the xml.
         dropdown = view.findViewById(R.id.medecin);

         btnSaveConsultation.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 try {
               date = (String) tvDate.getText();
               motif =  txtMotifConsultation.getText().toString().trim();

               Medecin medecin = (Medecin) dropdown.getSelectedItem();
               id_medecin = medecin.getId_medecin();
               String chaine = "\n"+date+"\n"+id_user+"\n"+motif+"\n"+id_medecin;

               if (date.isEmpty() || motif.isEmpty()  || date.equals(getString(R.string.date))){
                   Exception required = new Exception();
                  throw required;
               }else{
                  // Toast.makeText(getActivity().getBaseContext(), chaine, Toast.LENGTH_SHORT).show();
               addConsultation(date, motif, id_medecin, id_user);
               }

                     } catch (Exception e) {
                     e.printStackTrace();
                     Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();

                 }

             }
         });

        return view;
    }

    private void getMedecin(){

        String url = "http://"+MainActivity.IPADRESS+"/android/medicheck/list/medecin.php";
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
                    JSONArray ja = jo.getJSONArray("medecin");

                    ArrayList<Medecin> list = new ArrayList<Medecin>();
                    for (int i = 0; i < ja.length(); i++){
                        JSONObject element = ja.getJSONObject(i);
                        int id_medecin = element.getInt("id_medecin");
                        String prenom = element.getString("prenom");
                        String nom = element.getString("nom");
                        String spec = element.getString("specialite");
                        String chaine = "Prenom : "+prenom+"\nNom : "+nom;
                        Medecin med = new Medecin();
                        med.setId_medecin(id_medecin);
                        med.setPrenom(prenom);
                        med.setNom(nom);
                        med.setSpecialite(spec);

                        list.add(med);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        items.addAll(list);
                        ArrayAdapter<Medecin> adapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                        adapter.addAll(items);
                        dropdown.setAdapter(adapter);
                       }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void addConsultation(String date ,String motif,int id_medecin ,int id_user){
        String url = "http://"+MainActivity.IPADRESS+"/android/medicheck/add/consultation.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("date", date)
                .add("motif", motif)
                .add("id_medecin", String.valueOf(id_medecin))
                .add("id_user", String.valueOf(id_user))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);

                    String status = jo.getString("status");

                    if(status.equals("KO")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Ajout success ", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}