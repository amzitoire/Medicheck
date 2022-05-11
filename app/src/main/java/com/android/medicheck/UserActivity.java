package com.android.medicheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    private EditText txtLoginBD,txtPasswordBD;
    private String login , password;
    private Button btnAdd,btnUpdate,btnDelete,btnList;
    private BDUser bd = BDUser.getInstance(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtLoginBD = findViewById(R.id.txtLoginBD);
        txtPasswordBD = findViewById(R.id.txtPasswordBD);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnList = findViewById(R.id.btnList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLoginBD.getText().toString().trim();
                password = txtPasswordBD.getText().toString().trim();
                boolean booleanbd = bd.addUser(login,password);

                if(booleanbd){
                    Toast.makeText(UserActivity.this, "Utilisateur ajouté", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserActivity.this, "Utilisateur non ajouté", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLoginBD.getText().toString().trim();
                password = txtPasswordBD.getText().toString().trim();
                boolean booleanbd =  bd.updateUser(login,password);

                if(booleanbd){
                    Toast.makeText(UserActivity.this, "Utilisateur mis a jour", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserActivity.this, "Utilisateur non mis a jour", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLoginBD.getText().toString().trim();
                password = txtPasswordBD.getText().toString().trim();
                boolean booleanbd =  bd.DeleteUser(login);

                if(booleanbd){
                    Toast.makeText(UserActivity.this, "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserActivity.this, "Utilisateur non supprimé", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = bd.getUsers();
                String result = "";
                for (String user:list){
                    result += user+"\n";
                }
                Toast.makeText(UserActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        });


    }
}