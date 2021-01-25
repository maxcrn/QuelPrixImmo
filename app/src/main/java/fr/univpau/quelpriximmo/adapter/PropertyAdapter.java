package fr.univpau.quelpriximmo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import fr.univpau.quelpriximmo.Property;
import fr.univpau.quelpriximmo.R;

public class PropertyAdapter extends ArrayAdapter<Property> {

    public PropertyAdapter(Context context, ArrayList<Property> properties){
        super(context, 0, properties);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Property property = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_results, parent, false);
        }

        // Récupération de tous les champs

        TextView prixTypeVente = (TextView) convertView.findViewById(R.id.prixTypeVente);
        TextView dateVente = (TextView) convertView.findViewById(R.id.dateVente);
        TextView adresseVente = (TextView) convertView.findViewById(R.id.adresseVente);
        TextView surfaceVente1 = (TextView) convertView.findViewById(R.id.surfaceVente1);
        TextView batPieceVente1 = (TextView) convertView.findViewById(R.id.batPieceVente1);
        TextView surfaceTerrain = (TextView) convertView.findViewById(R.id.surfaceTerrain);



        // Formatage des différents attributs


             // Prix et type de la vente
        String strPrixTypeVente = "";
        if(property.getValeurFonciere() != null){
            strPrixTypeVente += property.getValeurFonciere() + " € ";
        }
        if (property.getNatureMutation() != null) {
            if(!strPrixTypeVente.equals("")){
                strPrixTypeVente += "/ " + property.getNatureMutation();
            }
            else{
                strPrixTypeVente += property.getNatureMutation();
            }
        }

            // Date
        String strDate = "";
        if(property.getDateMutation() != null){
            strDate = "Vendu le ";
            strDate += property.getDateMutation().substring(8);
            strDate += " - ";
            strDate += property.getDateMutation().substring(5,7);
            strDate += " - ";
            strDate += property.getDateMutation().substring(0,4);
        }


            // Adresse
        String strAdr = "";
        if(property.getNumVoie() != null){
            strAdr += property.getNumVoie() + " ";
        }
        if(property.getSufNum() != null){
            strAdr += property.getSufNum() + " ";
        }
        if(property.getTypeVoie() != null){
            strAdr += property.getTypeVoie() + " ";
        }
        if(property.getVoie() != null){
            strAdr += property.getVoie() + " ";
        }
        if(property.getCodePostal() != null){
            strAdr += property.getCodePostal() + " ";
        }
        if(property.getCommune() != null){
            strAdr += property.getCommune() + " ";
        }

            // Surface
        String strSurf = "";
        if(property.getSurfReelleBatie() != null){
            strSurf = property.getSurfReelleBatie() + " m²";
        }

            //  Type de batiment et nombre de pièces
        String strBatNbPieces = "";
        if(property.getTypeLocal() != null){
            strBatNbPieces = property.getTypeLocal();
            if(property.getNbPieces() != null){
                strBatNbPieces += " / " + property.getNbPieces() + " pièces";
            }
        }
        else if(property.getNbPieces() != null){
            strBatNbPieces += property.getNbPieces() + " pièces";
        }

            // Taille du terrain
        String strTerrain = "";
        if(property.getSurfTerrain() != null){
            strTerrain = "Terrain de " + property.getSurfTerrain() + " m²";
        }



        if(!strPrixTypeVente.equals("")){
            prixTypeVente.setText(strPrixTypeVente);
        }
        if(!strDate.equals("")){
            dateVente.setText(strDate);
        }
        if(!strAdr.equals("")){
            adresseVente.setText(strAdr);
        }
        if(!strSurf.equals("")){
            surfaceVente1.setText(strSurf);
        }
        if(!strBatNbPieces.equals("")){
            batPieceVente1.setText(strBatNbPieces);
        }
        if(!strTerrain.equals("")){
            surfaceTerrain.setVisibility(View.VISIBLE);
            surfaceTerrain.setText(strTerrain);
        }
        else{
            surfaceTerrain.setVisibility(View.GONE);
        }



        return convertView;
    }
}
