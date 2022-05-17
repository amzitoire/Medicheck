package com.android.medicheck.commons;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HospitalFragment extends Fragment implements OnMapReadyCallback{
    private JSONArray hopitalArray;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getHospitals();
        View view =inflater.inflate(R.layout.fragment_hospital, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng dakar = new LatLng(14.693425, -17.447938);
        googleMap.addMarker(new MarkerOptions().position(dakar).title("DKR").snippet("Région de dakar,Sénégal."));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dakar, 10));

        for (int i = 0; i < hopitalArray.length(); i++) {
            try {
                JSONObject element = null;
                element = hopitalArray.getJSONObject(i);
            String nom = element.getString("nom");
            String adresse = element.getString("adresse");
            String numero = element.getString("numero");
            String snippet = "Numero : "+numero+"\nAdresse : "+adresse;
            double latitude = element.getDouble("latitude");
            double longitude = element.getDouble("longitude");
            LatLng hopital = new LatLng(latitude,longitude);
            googleMap.addMarker(new MarkerOptions().position(hopital).title(nom).snippet(snippet));
            CircleOptions co = new CircleOptions()
                        .center(hopital)
                        .radius(10)
                        .fillColor(Color.GREEN)
                        .strokeColor(Color.WHITE)
                        .strokeWidth(6);
            googleMap.addCircle(co);
            } catch (JSONException e) {
                e.printStackTrace();
            }
                    }

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void getHospitals(){
        String url = "http://"+MainActivity.IPADRESS+"/android/medicheck/list/hopital.php";
        OkHttpClient client = new OkHttpClient();
        final JSONArray[] ja = {new JSONArray()};
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                final String message = getString(R.string.error_connection);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    ja[0] = jo.getJSONArray("hopital");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            hopitalArray = ja[0];
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}