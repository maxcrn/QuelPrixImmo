package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.univpau.quelpriximmo.adapter.PropertyAdapter;

public class Results extends AppCompatActivity {

    ArrayList<Property> resultats = new ArrayList<Property>();
    PropertyAdapter propertyAdapter;
    ListView listProperties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultats = (ArrayList<Property>) getIntent().getSerializableExtra("result");
        propertyAdapter = new PropertyAdapter(this, resultats);

        listProperties = (ListView) findViewById(R.id.listProperties);
        listProperties.setAdapter(propertyAdapter);

        if(resultats.isEmpty()){
            Toast.makeText(this, "Aucun résultat", Toast.LENGTH_LONG).show();
        }
    }





}