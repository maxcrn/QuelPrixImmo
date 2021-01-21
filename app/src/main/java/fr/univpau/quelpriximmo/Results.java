package fr.univpau.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

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

        // Portrait

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            propertyAdapter = new PropertyAdapter(this, resultats);

            listProperties = (ListView) findViewById(R.id.listProperties);
            listProperties.setAdapter(propertyAdapter);

            if(resultats.isEmpty()){
                Toast.makeText(this, "Aucun résultat", Toast.LENGTH_LONG).show();
            }
        }

        // Paysage

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            BarChart chart = (BarChart) findViewById(R.id.chart);
            BarData barData = new BarData();
            chart.setData(getDataSet());
            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(getXAxisValues()));
            chart.animateXY(2000, 2000);
            chart.invalidate();
        }
    }

    private ArrayList getXAxisValues() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("-100000€");
        xAxis.add("100000€ à 200000€");
        xAxis.add("200000€ à 300000€");
        xAxis.add("300000€ à 400000€");
        xAxis.add("400000€ à 500000€");
        xAxis.add("+500000€");
        return xAxis;
    }

    private BarData getDataSet() {


        ArrayList valueSet1 = new ArrayList();
        int i = 0;
        int first = 0;
        int second = 0;
        int third = 0;
        int fourth = 0;
        int fifth = 0;
        int sixth = 0;

        for(Property p : resultats){

            if(Float.parseFloat(p.getValeurFonciere()) < 100000){
                first++;
            }

            else if(Float.parseFloat(p.getValeurFonciere()) < 200000){
                second++;
            }

            else if(Float.parseFloat(p.getValeurFonciere()) < 300000){
                third++;
            }

            else if(Float.parseFloat(p.getValeurFonciere()) < 400000){
                fourth++;
            }

            else if(Float.parseFloat(p.getValeurFonciere()) < 500000){
                fifth++;
            }

            else{
                sixth++;
            }
        }

        BarEntry v1e1 = new BarEntry(0, first); // First
        valueSet1.add(v1e1);

        BarEntry v1e2 = new BarEntry(1, second); // First
        valueSet1.add(v1e2);

        BarEntry v1e3 = new BarEntry(2, third); // First
        valueSet1.add(v1e3);

        BarEntry v1e4 = new BarEntry(3, fourth); // First
        valueSet1.add(v1e4);

        BarEntry v1e5 = new BarEntry(4, fifth); // First
        valueSet1.add(v1e5);

        BarEntry v1e6 = new BarEntry(5, sixth); // First
        valueSet1.add(v1e6);



//        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
//        valueSet1.add(v1e1);
//        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
//        valueSet1.add(v1e2);
//        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
//        valueSet1.add(v1e3);
//        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
//        valueSet1.add(v1e4);
//        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
//        valueSet1.add(v1e5);
//        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
//        valueSet1.add(v1e6);
//
//        ArrayList valueSet2 = new ArrayList();
//        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
//        valueSet2.add(v2e1);
//        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
//        valueSet2.add(v2e2);
//        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
//        valueSet2.add(v2e3);
//        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
//        valueSet2.add(v2e4);
//        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
//        valueSet2.add(v2e5);
//        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
//        valueSet2.add(v2e6);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Nombre de propriétés vendues dans la tranche de prix");
        barDataSet1.setColor(Color.rgb(255,156,10));
//        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
//        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData dataSets = new BarData();
        dataSets.addDataSet(barDataSet1);
//        dataSets.addDataSet(barDataSet2);
        return dataSets;
    }



}