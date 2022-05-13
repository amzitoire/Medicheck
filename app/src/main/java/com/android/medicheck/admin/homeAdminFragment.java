package com.android.medicheck.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.medicheck.BDUser;
import com.android.medicheck.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class homeAdminFragment extends Fragment {

    private EditText txtLoginBD,txtPasswordBD;
    private String login , password;
    private Button btnAdd,btnUpdate,btnDelete,btnList;
    private BDUser bd ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    bd = BDUser.getInstance(getContext());
    View view =  inflater.inflate(R.layout.fragment_home_admin, container, false);



        txtLoginBD = view.findViewById(R.id.txtLoginBD);
        txtPasswordBD = view.findViewById(R.id.txtPasswordBD);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnList = view.findViewById(R.id.btnList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = txtLoginBD.getText().toString().trim();
                password = txtPasswordBD.getText().toString().trim();
                boolean booleanbd = bd.addUser(login,password);

                if(booleanbd){
                    Toast.makeText(getContext(), "Utilisateur ajouté", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Utilisateur non ajouté", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Utilisateur mis a jour", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Utilisateur non mis a jour", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Utilisateur supprimé", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Utilisateur non supprimé", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return view;

    }
}