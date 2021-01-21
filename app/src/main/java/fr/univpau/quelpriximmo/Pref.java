package fr.univpau.quelpriximmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

public class Pref extends AppCompatActivity {

    SharedPreferences pref;
    Slider sliderDist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Float distance = Float.parseFloat(pref.getString("distance", null));
        sliderDist = (Slider) findViewById(R.id.sliderDist);
        TextView distTxt = (TextView) findViewById(R.id.distTxt);


        sliderDist.setValue(distance);
        distTxt.setText(Integer.toString((int)sliderDist.getValue()) + " mètres");
        Slider.OnChangeListener onChangeListener = new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                distTxt.setText(Integer.toString((int)sliderDist.getValue()) + " mètres");
            }
        };
        sliderDist.addOnChangeListener(onChangeListener);
    }


    public void validPref(View v){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("distance", Integer.toString((int)sliderDist.getValue()));
        editor.apply();
        Intent i = new Intent();
        i.setClass(this, SearchForm.class);
        startActivity(i);
    }


}