package com.android.medicheck.models;

import com.android.medicheck.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Antecedent {
    private int id_antecedent;
    private String description;
    private int id_dossier;
    public Dossier dossier;

    public String getDescription() {
        return description;
    }

    public int getId_dossier() {
        return id_dossier;
    }

    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId_antecedent() {
        return id_antecedent;
    }

    public void setId_antecedent(int id_antecedent) {
        this.id_antecedent = id_antecedent;
    }


    public static ArrayList<Antecedent> getAntecedents(){
        ArrayList<Antecedent> antecedents= new ArrayList<Antecedent>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/antecedent.php";

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
                    JSONArray ja = jo.getJSONArray("antecedent");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Antecedent antecedent = new Antecedent();
                        //id_antecedent	description	id_dossier

                        antecedent.setId_antecedent(element.getInt("id_antecedent"));
                        antecedent.setDescription(element.getString("description"));
                        antecedent.setId_dossier(element.getInt("id_dossier"));
                        antecedent.setDossier(Dossier.findById(element.getInt("id_dossier")));

                        antecedents.add(antecedent);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return antecedents;
    }

    public static Antecedent findById(int id){
        Antecedent antecedent = new Antecedent();

        for (Antecedent element:getAntecedents()) {
            if ((element.getId_antecedent() == id)){
                antecedent = element;
            }
        }

        return antecedent;
    }
}
