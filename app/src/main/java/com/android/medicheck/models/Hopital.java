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

public class Hopital {
    private int id_hopital;

    private String nom;

    private String adresse;

    private String numero;

    public String coordonnee;

    public int getId_hopital() {
        return id_hopital;
    }

    public void setId_hopital(int id_hopital) {
        this.id_hopital = id_hopital;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public static final void localisationMaps() {
    }

    public static ArrayList<Hopital> getHopitals(){
        ArrayList<Hopital> hopitals= new ArrayList<Hopital>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/hopital.php";

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
                    JSONArray ja = jo.getJSONArray("hopital");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Hopital hopital = new Hopital();
                        //id_hopital	nom	adresse	numero	coordonees
                        hopital.setId_hopital(element.getInt("id_hopital"));
                        hopital.setNom(element.getString("nom"));
                        hopital.setAdresse(element.getString("adresse"));
                        hopital.setNumero(element.getString("numero"));
                        hopital.coordonnee = element.getString("coordonees");

                        hopitals.add(hopital);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return hopitals;
    }

    public static Hopital findById(int id){
        Hopital hopital = new Hopital();

        for (Hopital element:getHopitals()) {
            if ((element.getId_hopital() == id)){
                hopital = element;
            }
        }

        return hopital;
    }

}
