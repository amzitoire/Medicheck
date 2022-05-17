package com.android.medicheck.admin;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddAppointmentFragment extends Fragment {
    public static int id_consultation;
    private TextView tvDate;
    private EditText txtMotif;
    private Button btnSaveAppointment;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String dateStr;
    private String motif;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_appointment, container, false);
        btnSaveAppointment = view.findViewById(R.id.btnSaveAppointment);
        tvDate = view.findViewById(R.id.tvDate);
        txtMotif = view.findViewById(R.id.txtMotif);

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

        btnSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dateStr = (String) tvDate.getText();
                    motif =  txtMotif.getText().toString().trim();

                    String chaine = "\n"+ dateStr+""+ motif+""+ id_consultation+"";

                    if (dateStr.isEmpty() || motif.isEmpty() || dateStr.equals(getString(R.string.date)) ){
                        Exception required = new Exception();
                        throw required;
                    }else{
                        // Toast.makeText(getActivity().getBaseContext(), chaine, Toast.LENGTH_SHORT).show();
                        addAppointment(dateStr, motif, id_consultation);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    private void addAppointment(String dateStr, String motif, int id_consultation) {
        String url = "http://"+ MainActivity.IPADRESS+"/android/medicheck/add/appointment.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("date", dateStr)
                .add("motif", motif)
                .add("id_consultation", String.valueOf(id_consultation))
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
                                Toast.makeText(getActivity().getBaseContext(), getString(R.string.error_field), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getBaseContext(), "Ajout success ", Toast.LENGTH_SHORT).show();

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