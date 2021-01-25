package fr.univpau.quelpriximmo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;

import fr.univpau.quelpriximmo.AsyncTask.JsonGetter;

public class SearchForm extends AppCompatActivity {


    private Double latitude, longitude;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);

        getLocation();

        pref = PreferenceManager.getDefaultSharedPreferences(this);

        if(pref.getString("distance","unknown").equals("unknown")){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("distance", "500");
            editor.apply();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.preference){
            Intent i = new Intent();
            i.setClass(this, Pref.class);
            startActivity(i);
        }


        return super.onOptionsItemSelected(item);
    }

    public void okSearch(View v) throws IOException {

        final int rotation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();

        if(rotation == Surface.ROTATION_0) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else if(rotation == Surface.ROTATION_90) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else if(rotation == Surface.ROTATION_270){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE );
        }

        RadioButton radioAppt = findViewById(R.id.radioAppt);
        RadioButton radioHome = findViewById(R.id.radioHome);
        EditText nbSearchAnsMin = findViewById(R.id.nbSearchMin);
        EditText nbSearchAnsMax = findViewById(R.id.nbSearchMax);



        String search = "https://api.cquest.org/dvf?";
        String distance = pref.getString("distance","unknown");
        // Test pour riches
//        latitude = 44.8421392;
//        longitude = -0.5745577;
        search += "lat=" + latitude + "&lon=" + longitude + "&dist=" + distance;
        System.out.println(search); // Print de l'URL


        String typeBien = "";
        if(radioAppt.isChecked()){
            typeBien = "Appartement";
        }
        else if(radioHome.isChecked()){
            typeBien = "Maison";
        }


        String nbPiecesMin = "";
        nbPiecesMin += nbSearchAnsMin.getText().toString();
        String nbPiecesMax = "";
        nbPiecesMax += nbSearchAnsMax.getText().toString();

        if(typeBien.equals("")){
            Toast.makeText(this, "Merci de choisir le type de bien", Toast.LENGTH_SHORT).show();
        }
        else if(nbPiecesMin.equals("")){
            Toast.makeText(this, "Merci de choisir le nombre de pièces minimum", Toast.LENGTH_SHORT).show();
        }
        else if(nbPiecesMax.equals("")){
            Toast.makeText(this, "Merci de choisir le nombre de pièces maximum", Toast.LENGTH_SHORT).show();
        }
        else if(Integer.parseInt(nbPiecesMin)>Integer.parseInt(nbPiecesMax)){
            Toast.makeText(this, "Le nombre de pièces minimum doit être inférieur au nombre de pièces maximum", Toast.LENGTH_SHORT).show();
        }
        else{
            JsonGetter jsonGetter = new JsonGetter(this, typeBien, nbPiecesMin, nbPiecesMax);
            jsonGetter.execute(search);
        }

        System.out.println("Bien recherché : " + typeBien + " avec " + nbPiecesMin + " pièces à " + nbPiecesMax);
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