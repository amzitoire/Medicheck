package com.android.medicheck.admin;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddMedicalFileFragment extends Fragment {

    public static int id_user;
    private EditText txtDateNaissance,txtPoids;
    private Button btnSave;
    private TextView tvDate;
    private Spinner dropdown;
    private ArrayList<String> genre = new ArrayList<String>() ;

    private String date ;
    private String poids;
    private String sexe;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medical_file, container, false);

        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                tvDate.setText(date);
            }
        };

        txtPoids = view.findViewById(R.id.txtPoids);
        dropdown = view.findViewById(R.id.listSexe);

        genre.add("Masculin");
        genre.add("Feminin");

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.addAll(genre);
        dropdown.setAdapter(adapter);

        btnSave = view.findViewById(R.id.btnSaveDossier);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                date = (String) tvDate.getText();
                sexe = dropdown.getSelectedItem().toString();
                poids = txtPoids.getText().toString().trim();
                String chaine= "\n"+date+"\n"+sexe+"\n"+poids+"\n"+id_user;
                    if (date.isEmpty() || poids.isEmpty()  || date.equals(getString(R.string.date))){
                        throw new Exception();
                    }else{
                       // Toast.makeText(getActivity().getBaseContext(), chaine, Toast.LENGTH_SHORT).show();
                        addMedicalFile(date, poids, sexe, id_user);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    private void addMedicalFile(String date, String poids, String sexe, int id_user) {
        String url = "http://"+ MainActivity.IPADRESS+"/android/medicheck/add/dossier.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("date", date)
                .add("poids", poids)
                .add("sexe", sexe)
                .add("id_user", String.valueOf(id_user))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = "une erreur s'est produite.";
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
                                Toast.makeText(getActivity().getBaseContext(), "un dossier existe déja", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        String message = jo.getString("message");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), message, Toast.LENGTH_SHORT).show();

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