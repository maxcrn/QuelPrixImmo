package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    ArrayList<Property> resultats = new ArrayList<Property>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        resultats = (ArrayList<Property>) getIntent().getSerializableExtra("result");
        System.out.println(resultats);

        for(Property p : resultats){
            System.out.println(p.getValeurFonciere()+ " / " + p.getNatureMutation());
            System.out.println(p.getDateMutation());
            System.out.println(p.getNumVoie() + p.getSufNum() + " " + p.getTypeVoie() + " " + p.getVoie());
            System.out.println(p.getSurfReelleBatie() + "m²");
            System.out.println(p.getTypeLocal() + " " + p.getNbPieces() + " pièces");
        }

    }
}