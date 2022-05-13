package com.android.medicheck.models;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Consultation {

    private int id_consultation;

    private Date date_consultation;

    private String motif_consultation;

    private String resultat;

    private int id_dossier;

    private String statuts;

    public Dossier dossier;

    public Medecin medecin;

    public int getId_consultation() {
        return id_consultation;
    }

    public void setId_consultation(int id_consultation) {
        this.id_consultation = id_consultation;
    }

    public Date getDate_consultation() {
        return date_consultation;
    }

    public void setDate_consultation(Date date_consultation) {
        this.date_consultation = date_consultation;
    }

    public String getMotif_consultation() {
        return motif_consultation;
    }

    public void setMotif_consultation(String motif_consultation) {
        this.motif_consultation = motif_consultation;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public int getId_dossier() {
        return id_dossier;
    }

    public void setId_dossier(int id_dossier) {
        this.id_dossier = id_dossier;
    }

    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public static ArrayList<Consultation> getConsultations(){
        ArrayList<Consultation> consultations= new ArrayList<Consultation>();
        String ipadress = MainActivity.IPADRESS;

        String url = "http://"+ipadress+"/android/medicheck/list/consultation.php";

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
                    JSONArray ja = jo.getJSONArray("consultation");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject element = ja.getJSONObject(i);
                        Consultation consultation = new Consultation();
                        //id_consultation	date_consultation	motif	resultat	status	id_dossier id_medecin
                        consultation.setId_consultation(element.getInt("id_consultation"));
                        consultation.setDate_consultation(new SimpleDateFormat("yyyy-MM-dd").parse(element.getString("date_consultation")));
                        consultation.setMotif_consultation(element.getString("motif"));
                        consultation.setDossier(Dossier.findById(element.getInt("id_dossier")));
                        consultation.setMedecin(Medecin.findById(element.getInt("id_medecin")));
                        consultation.setResultat(element.getString("resultat"));
                        consultation.setStatuts(element.getString("status"));

                        consultations.add(consultation);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return consultations;
    }

    public static Consultation findById(int id){
        Consultation consultation = new Consultation();

        for (Consultation element:getConsultations()) {
            if ((element.getId_consultation() == id)){
                consultation = element;
            }
        }

        return consultation;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id_consultation=" + id_consultation +
                ", date_consultation=" + date_consultation +
                ", motif_consultation='" + motif_consultation + '\'' +
                ", resultat='" + resultat + '\'' +
                ", id_dossier=" + id_dossier +
                ", statuts='" + statuts + '\'' +
                ", dossier=" + dossier +
                ", medecin=" + medecin +
                '}';
    }
}

