package com.android.medicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.medicheck.patient.PatientNavActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText txtLogin, txtPassword;
    private Button btnConnect;
    private String password;
    public static String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Liaison entre variables et composants
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        btnConnect = findViewById(R.id.btnConnect);


        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if(login.isEmpty() || password.isEmpty()){
                    String message = getString(R.string.error_field);
                    //Message d'alert
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }else{
                   /* if(login.equals("admin") && password.equals("admin"))
                    {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    }else {

                        Intent intent = new Intent(MainActivity.this, PatientNavActivity.class);
                        startActivity(intent);
                    }*/
                    authentification();


                }
            }
        });
    }

    public void authentification(){
        String url = "http://192.168.1.14/android/medicheck/connexion.php?login="+login+"&password="+password;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);

                    String status = jo.getString("status");

                    JSONArray user = jo.getJSONArray("user");
                    JSONObject userJSON= user.getJSONObject(0);

                    String type  = userJSON.getString("type");

                    if(status.equals("KO")){
                        final String message = getString(R.string.error_parameters);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                       /* Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                        intent.putExtra("LOGIN", login);
                        startActivity(intent);*/

                        if(type.equals("admin"))
                        {
                            Intent intent = new Intent(MainActivity.this, UserActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(MainActivity.this, PatientNavActivity.class);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}