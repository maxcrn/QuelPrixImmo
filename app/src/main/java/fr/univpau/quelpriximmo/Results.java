package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

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


        for(Property p : resultats){
            System.out.println(p.getValeurFonciere()+ " / " + p.getNatureMutation());
            System.out.println(p.getDateMutation());
            System.out.println(p.getNumVoie() + p.getSufNum() + " " + p.getTypeVoie() + " " + p.getVoie());
            System.out.println(p.getSurfReelleBatie() + "m²");
            System.out.println(p.getTypeLocal() + " " + p.getNbPieces() + " pièces");
        }
    }





}