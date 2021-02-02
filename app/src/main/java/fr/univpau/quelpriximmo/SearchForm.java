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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.univpau.quelpriximmo.AsyncTask.JsonGetter;

public class SearchForm extends AppCompatActivity {


    private Double latitude, longitude;
    SharedPreferences pref;
    RangeSlider piecesSlider;
    TextView nbPiecesTxt;
    float nbPiecesMin;
    float nbPiecesMax;

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

        piecesSlider = findViewById(R.id.piecesSlider);

        nbPiecesTxt = findViewById(R.id.nbPiecesTxt);
        piecesSlider.addOnChangeListener(onChangeListener);
        nbPiecesMin = 1;
        nbPiecesMax = 2;
        List<Float> valuesCreate = new ArrayList<Float>();
        valuesCreate.add(nbPiecesMin);
        valuesCreate.add(nbPiecesMax);
        piecesSlider.setValues(valuesCreate);
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
            startActivityForResult(i, 1);
        }


        return super.onOptionsItemSelected(item);
    }

    RangeSlider.OnChangeListener onChangeListener = new RangeSlider.OnChangeListener() {
        @Override
        public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
            List<Float> values = slider.getValues();
            nbPiecesMin = Collections.min(values);
            nbPiecesMax = Collections.max(values);
            String nbPiecesMinTxt = Integer.toString(Math.round(nbPiecesMin));
            String nbPiecesMaxTxt = Integer.toString(Math.round(nbPiecesMax));
            String txtTemp = "";
            if(nbPiecesMinTxt.equals(nbPiecesMaxTxt)){
                if(nbPiecesMaxTxt.equals("15")){
                    txtTemp += nbPiecesMinTxt + " pièces ou plus.";
                }
                else if(nbPiecesMaxTxt.equals("1")){
                    txtTemp += nbPiecesMinTxt + " pièce.";
                }
                else{
                    txtTemp += nbPiecesMinTxt + " pièces.";
                }
            }
            else{
                txtTemp += "De ";
                txtTemp += nbPiecesMinTxt;
                txtTemp += " à ";
                if(nbPiecesMaxTxt.equals("15")){
                    txtTemp += nbPiecesMaxTxt + " pièces ou plus.";
                }
                else if(nbPiecesMaxTxt.equals("1")){
                    txtTemp += nbPiecesMaxTxt + " pièce.";
                }
                else{
                    txtTemp += nbPiecesMaxTxt + " pièces.";
                }
            }
            nbPiecesTxt.setText(txtTemp);
        }
    };

    public void okSearch(View v) throws IOException {



        RadioButton radioAppt = findViewById(R.id.radioAppt);
        RadioButton radioHome = findViewById(R.id.radioHome);



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


        String nbPiecesMinStr = Integer.toString(Math.round(nbPiecesMin));
        String nbPiecesMaxStr = Integer.toString(Math.round(nbPiecesMax));

        if(typeBien.equals("")) {
            Toast.makeText(this, "Merci de choisir le type de bien", Toast.LENGTH_SHORT).show();
        }
        else if(latitude == null || longitude == null){
            Toast.makeText(this, "Merci d'autoriser cette application à utiliser la localisation dans les paramètres de votre téléphone", Toast.LENGTH_LONG).show();
        }
        else{
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

            JsonGetter jsonGetter = new JsonGetter(this, typeBien, nbPiecesMinStr, nbPiecesMaxStr);
            jsonGetter.execute(search);
        }



        System.out.println("Bien recherché : " + typeBien + " avec " + nbPiecesMinStr + " pièces à " + nbPiecesMaxStr);
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