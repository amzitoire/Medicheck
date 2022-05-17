package com.android.medicheck.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class addPatientFragment extends Fragment {

    private EditText txtnom ;
    private EditText txtprenom ;
    private EditText txtlogin ;
    private EditText txtpassword ;
    private Button btnSave;

    private String nom;
    private String prenom;
    private String login;
    private String password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view =inflater.inflate(R.layout.fragment_add_patient, container, false);

          txtnom = view.findViewById(R.id.txtNom);
          txtprenom = view.findViewById(R.id.txtPrenom);
          txtlogin = view.findViewById(R.id.txtLogin);
          txtpassword = view.findViewById(R.id.txtPassword);
          btnSave = view.findViewById(R.id.btnAddPatient);

          btnSave.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  nom = txtnom.getText().toString().trim();
                  prenom = txtprenom.getText().toString().trim();
                  login = txtlogin.getText().toString().trim();
                  password = txtpassword.getText().toString().trim();

                  String chaine  = ""+nom+""+prenom+""+login+""+password+"";

              if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || password.isEmpty()){
                  Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();
              }else {
                  //    Toast.makeText(getActivity().getBaseContext(), chaine, Toast.LENGTH_SHORT).show();
                  addPatient(nom,prenom,login,password);
              }
              }

          });



          return view;
    }

    private void addPatient(String nom, String prenom, String login, String password) {
        String url = "http://"+ MainActivity.IPADRESS+"/android/medicheck/add/patient.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("nom", nom)
                .add("prenom", prenom)
                .add("login", login)
                .add("password", password)
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
                                Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field_login), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Ajout success", Toast.LENGTH_SHORT).show();

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