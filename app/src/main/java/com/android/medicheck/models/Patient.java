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

public class Patient extends  Utilisateur{

    public static ArrayList<Utilisateur> getPatients(){
        ArrayList<Utilisateur> utilisateurs= new ArrayList<Utilisateur>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/patient.php";

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
                    JSONArray ja = jo.getJSONArray("patient");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Utilisateur utilisateur = new Patient();
                        //id_user nom prenom login password type
                        utilisateur.setId(element.getInt("id_user"));
                        utilisateur.setNom(element.getString("nom"));
                        utilisateur.setPrenom(element.getString("prenom"));
                        utilisateur.setLogin(element.getString("login"));
                        utilisateur.setPassword(element.getString("password"));
                        utilisateur.setType(element.getString("type"));

                        utilisateurs.add(utilisateur);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return utilisateurs;
    }

    public static Utilisateur findById(int id){
        Utilisateur utilisateur = new Patient();

        for (Utilisateur element:getPatients()) {
            if ((element.getId() == id)){
                utilisateur = element;
            }
        }

        return utilisateur;
    }
}
