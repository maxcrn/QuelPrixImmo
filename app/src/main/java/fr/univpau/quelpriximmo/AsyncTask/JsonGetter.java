package fr.univpau.quelpriximmo.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import fr.univpau.quelpriximmo.Property;
import fr.univpau.quelpriximmo.Results;

public class JsonGetter extends AsyncTask<String, String, ArrayList<Property>> {

    private Context asyncTaskContext;
    private ProgressDialog progressDialog;
    private String asyncTaskTypeBien;
    private String asyncTaskNbPiecesMin;
    private String asyncTaskNbPiecesMax;

    public JsonGetter(Context context, String typeBien, String nbPiecesMin, String nbPiecesMax) {
        asyncTaskContext = context;
        progressDialog = new ProgressDialog(asyncTaskContext);
        asyncTaskTypeBien = typeBien;
        asyncTaskNbPiecesMin = nbPiecesMin;
        asyncTaskNbPiecesMax = nbPiecesMax;
    }

    String result = "";

    protected void onPreExecute() {
        progressDialog.setMessage("Recherche des biens en cours...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface arg0) {
                JsonGetter.this.cancel(true);
            }
        });
    }

    @Override
    protected ArrayList<Property> doInBackground(String... params) {

        // Recuperation du fichier JSON sur l'API
        HttpsURLConnection con;
        try {
            URL url1 = new URL(params[0]);
            con = (HttpsURLConnection) url1.openConnection();
            con.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            result = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Création de la liste de Property qui sera le resultat
        ArrayList<Property> propertiesResult = new ArrayList<Property>();
        try {
            final JSONObject obj = new JSONObject(result);
            System.out.println(result.length());
            final JSONArray features = obj.getJSONArray("features");
            final int n = features.length();

            System.out.println(features.length());
            for (int i = 0; i < n; ++i) {
                Property newProperty = new Property();
                final JSONObject feature = features.getJSONObject(i);
                final JSONObject property = feature.getJSONObject("properties");
                if(property.has("valeur_fonciere")){
                    newProperty.setValeurFonciere(String.valueOf(property.getInt("valeur_fonciere")));
                }
                if(property.has("nature_mutation")){
                    newProperty.setNatureMutation(property.getString("nature_mutation"));
                }
                if(property.has("date_mutation")){
                    newProperty.setDateMutation(property.getString("date_mutation"));
                }
                if(property.has("numero_voie")){
                    newProperty.setNumVoie(property.getString("numero_voie"));
                }
                if(property.has("suffixe_numero")){
                    newProperty.setSufNum(property.getString("suffixe_numero"));
                }
                if(property.has("type_voie")){
                    newProperty.setTypeVoie(property.getString("type_voie"));
                }
                if(property.has("voie")){
                    newProperty.setVoie(property.getString("voie"));
                }
                if(property.has("surface_relle_bati")){
                    newProperty.setSurfReelleBatie(String.valueOf(property.getInt("surface_relle_bati")));
                }
                if(property.has("type_local")){
                    newProperty.setTypeLocal(property.getString("type_local"));
                }
                if(property.has("nombre_pieces_principales")){
                    newProperty.setNbPieces(property.getString("nombre_pieces_principales"));
                }
                if(property.has("surface_terrain")){
                    newProperty.setSurfTerrain(property.getString("surface_terrain"));
                }
                if(newProperty.getNbPieces() != null && newProperty.getTypeLocal() != null){
                    if(newProperty.getTypeLocal().equals(asyncTaskTypeBien)){
                        if(asyncTaskNbPiecesMax.equals("15")){
                            if(Integer.parseInt(newProperty.getNbPieces()) >= Integer.parseInt(asyncTaskNbPiecesMin)){
                                propertiesResult.add(newProperty);
                            }
                        }
                        else{
                            if(Integer.parseInt(newProperty.getNbPieces()) >= Integer.parseInt(asyncTaskNbPiecesMin) && Integer.parseInt(newProperty.getNbPieces()) <= Integer.parseInt(asyncTaskNbPiecesMax)){
                                propertiesResult.add(newProperty);
                            }
                        }
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertiesResult;
    }


    protected void onPostExecute(ArrayList<Property> propertiesResult) {
        this.progressDialog.dismiss();
        System.out.println(propertiesResult);
        Intent i = new Intent();
        i.setClass(asyncTaskContext, Results.class);
        i.putExtra("result", propertiesResult);
        asyncTaskContext.startActivity(i);
    }
}
