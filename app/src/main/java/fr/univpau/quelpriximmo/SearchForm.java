package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class SearchForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);
    }

    public void okSearch(View v){

        RadioButton radioAppt = findViewById(R.id.radioAppt);
        RadioButton radioHome = findViewById(R.id.radioHome);
        EditText nbSearchAns = findViewById(R.id.nbSearchAns);

        String search = "https://api.cquest.org/dvf?";

        getLocation();

    }

    private Location getLocation(){

        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // Définition des critères de recherche de localisation
        Criteria criterias = new Criteria();
        criterias.setAccuracy(Criteria.ACCURACY_FINE); // Precision
        criterias.setAltitudeRequired(false); // Altitude
        criterias.setBearingRequired(false); // Direction
        criterias.setSpeedRequired(false); // Vitesse
        criterias.setCostAllowed(true); // Coûts
        criterias.setPowerRequirement(Criteria.POWER_HIGH); // Energie

        String provider = locationManager.getBestProvider(criterias, true);
        Log.d("GPS", "Fournisseur : " + provider);

        @SuppressLint("MissingPermission")
        Location location = locationManager.getLastKnownLocation(provider);
        System.out.println(location);
        Log.d("GPS", "Localisation : " + location.toString());
        String coord = String.format("Latitude : %f - Longitude : %f\n", location.getLatitude(), location.getLongitude());
        Log.d("GPS", "Coordonnees : " + coord);

        return location;

    }

}