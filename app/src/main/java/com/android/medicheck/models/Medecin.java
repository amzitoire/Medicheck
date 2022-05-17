package com.android.medicheck.models;

import com.android.medicheck.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Medecin {
    public int id_medecin;

    public String nom;

    public String prenom;

    public String specialite;

    private String numero;

    public static ArrayList<Medecin> getMedecins(){
        ArrayList<Medecin> medecins= new ArrayList<Medecin>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/medecin.php";

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
                    JSONArray ja = jo.getJSONArray("medecin");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Medecin medecin = new Medecin();
                        //id_medecin	nom	prenom	specialite	numero
                        medecin.setId_medecin(element.getInt("id_medecin"));
                        medecin.setNom(element.getString("nom"));
                        medecin.setPrenom(element.getString("prenom"));
                        medecin.setNumero(element.getString("numero"));
                        medecin.setSpecialite(element.getString("specialite"));

                        medecins.add(medecin);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return medecins;
    }

    public static Medecin findById(int id){
        Medecin medecin = new Medecin();

        for (Medecin element:getMedecins()) {
            if ((element.getId_medecin() == id)){
                medecin = element;
            }
        }

        return medecin;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return
                "\nNom: Dr " + nom +
                "\nSpecialite: " + specialite ;
    }
}

