package com.android.medicheck.models;

import com.android.medicheck.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Dossier {
    private int id_dossier;
    private Date birthDate;
    private int poids;
    private int taille;
    private String sexe;
    private int id_patient;

    public static ArrayList<Dossier> getDossiers(){
        ArrayList<Dossier> dossiers= new ArrayList<Dossier>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/dossier.php";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("dossier");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Dossier dossier = new Dossier();
                        //id_dossier
                        //date_naissance
                        //poids
                        //sexe
                        //id_patient
                        dossier.setId_dossier(element.getInt("id_dossier"));
                        dossier.setBirthDate(new SimpleDateFormat("yyyy-MM-dd").parse(element.getString("date_naissance")));
                        dossier.setPoids(element.getInt("poids"));
                        dossier.setSexe(element.getString("sexe"));
                        dossier.setId_patient(element.getInt("id_patient"));

                        dossiers.add(dossier);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return dossiers;
    }

    public static Dossier findById(int id){
        Dossier dossier = new Dossier();

        for (Dossier element:getDossiers()) {
            if ((element.getId_dossier() == id)){
                dossier = element;
            }
        }

        return dossier;
    }


    public int getId_dossier() {
        return id_dossier;
    }

    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }

}

