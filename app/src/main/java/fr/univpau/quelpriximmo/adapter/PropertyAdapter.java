package fr.univpau.quelpriximmo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        TextView surfaceVente2 = (TextView) convertView.findViewById(R.id.surfaceVente2);
        TextView batPieceVente2 = (TextView) convertView.findViewById(R.id.batPieceVente2);

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
            String date = property.getDateMutation();
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


        if(!strPrixTypeVente.equals("")){
            prixTypeVente.setText(strPrixTypeVente);
        }
        prixTypeVente.setText(property.getValeurFonciere() + " € / " + property.getNatureMutation());
        if(!strDate.equals("")){
            dateVente.setText(strDate);
        }
        if(!strAdr.equals("")){
            adresseVente.setText(strAdr);
        }



        return convertView;
    }
}
