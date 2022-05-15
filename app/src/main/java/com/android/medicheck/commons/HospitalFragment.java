package com.android.medicheck.commons;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.medicheck.MainActivity;
import com.android.medicheck.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HospitalFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hospital, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getHospitals(mMap);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private void getHospitals(GoogleMap map){
        String url = "http://"+ MainActivity.IPADRESS+"/android/medicheck/list/hopital.php";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("hopital");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String nom = element.getString("nom");
                        String adresse = element.getString("adresse");
                        String numero = element.getString("numero");
                        String snippet = "Numero : "+numero+"\nAdresse : "+adresse;
                        double latitude = element.getDouble("latitude");
                        double longitude = element.getDouble("longitude");
                        LatLng hopital = new LatLng(latitude,longitude);
                        map.addMarker(new MarkerOptions().position(hopital).title(nom).snippet(snippet));
                        CircleOptions co = new CircleOptions()
                                .center(hopital)
                                .radius(600)
                                .fillColor(Color.GREEN)
                                .strokeColor(Color.WHITE)
                                .strokeWidth(6);
                        mMap.addCircle(co);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}