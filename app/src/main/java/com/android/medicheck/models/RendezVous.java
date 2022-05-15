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

public class RendezVous {

private int id_rv;

private Date date_rv;

private String motif_rv;

private int id_consulation;

private String statuts;

public Consultation consultation;

        public int getId_rv() {
                return id_rv;
        }

        public void setId_rv(int id_rv) {
                this.id_rv = id_rv;
        }

        public Date getDate_rv() {
                return date_rv;
        }

        public void setDate_rv(Date date_rv) {
                this.date_rv = date_rv;
        }

        public String getMotif_rv() {
                return motif_rv;
        }

        public void setMotif_rv(String motif_rv) {
                this.motif_rv = motif_rv;
        }

        public int getId_consulation() {
                return id_consulation;
        }

        public void setId_consulation(int id_consulation) {
                this.id_consulation = id_consulation;
        }

        public String getStatuts() {
                return statuts;
        }

        public void setStatuts(String statuts) {
                this.statuts = statuts;
        }

        public Consultation getConsultation() {
                return consultation;
        }

        public void setConsultation(Consultation consultation) {
                this.consultation = consultation;
        }


        public static ArrayList<RendezVous> getRendezVous(){
                ArrayList<RendezVous> list= new ArrayList<RendezVous>();
                String ipadress = MainActivity.IPADRESS;
                String url = "http://"+ipadress+"/android/medicheck/list/rendezVous.php";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                                RendezVous res = new RendezVous();
                                res.setStatuts("NOK");
                                list.add(res);
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                                try {
                                        String result = response.body().string();
                                        JSONObject jo = new JSONObject(result);
                                        JSONArray ja = jo.getJSONArray("rendez_vous");
                                        for (int i = 0; i < ja.length(); i++) {
                                                JSONObject element = ja.getJSONObject(i);
                                                RendezVous rendezVous = new RendezVous();
                                                // id_rv	date_rv	motif_rv	status	id_consultation
                                                rendezVous.setId_rv(element.getInt("id_rv"));
                                                rendezVous.setDate_rv(new SimpleDateFormat("yyyy-mm-dd").parse(element.getString("date_rv")));
                                                rendezVous.setMotif_rv(element.getString("motif_rv"));
                                                rendezVous.setStatuts(element.getString("status"));
                                                rendezVous.setId_consulation(element.getInt("id_consultation"));
//                                                rendezVous.setConsultation(Consultation.findById(element.getInt("id_consultation")));
                                                list.add(rendezVous);
                                        }
                                }
                                catch (Exception e){
                                        e.printStackTrace();
                                }
                        }
                });
                return list;
        }

        public static RendezVous findById(int id){
                RendezVous rendezVous = new RendezVous();

                for (RendezVous element:getRendezVous()) {
                        if ((element.getId_rv() == id)){
                                rendezVous = element;
                        }
                }

                return rendezVous;
        }

        public static ArrayList<RendezVous> findByIdUser(int id){
                ArrayList<RendezVous> list = new ArrayList<RendezVous>();
                for (RendezVous element:getRendezVous()) {
                        Dossier dossier = element.consultation.getDossier();
                        int id_user = dossier.getId_patient();
                        if ((id_user == id)){
                                list.add(element);
                        }
                }

                return list;
        }

        @Override
        public String toString() {
                return "Date : " + this.date_rv + "\nMotif : '" + this.motif_rv  ;
        }
}