package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import fr.univpau.quelpriximmo.AsyncTask.JsonGetter;

public class SearchForm extends AppCompatActivity {


    private Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);

        getLocation();
    }

    public void okSearch(View v) throws IOException {

        RadioButton radioAppt = findViewById(R.id.radioAppt);
        RadioButton radioHome = findViewById(R.id.radioHome);
        EditText nbSearchAns = findViewById(R.id.nbSearchAns);

        String search = "https://api.cquest.org/dvf?";
        String distance = "2000";
        search += "lat=" + latitude + "&lon=" + longitude + "&dist=" + distance;
        System.out.println(search); // Print de l'URL



        String typeBien = "";
        if(radioAppt.isChecked()){
            typeBien = "Appartement";
        }
        else if(radioHome.isChecked()){
            typeBien = "Maison";
        }

        String nbPieces = nbSearchAns.getText().toString();

        JsonGetter jsonGetter = new JsonGetter(this, typeBien, nbPieces);
        jsonGetter.execute(search);

        System.out.println("Bien recherché : " + typeBien + " avec " + nbPieces + " pièces");
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setNumUpdates(2);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }
        };
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);

        LocationServices.getFusedLocationProviderClient(this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        });

    }
}