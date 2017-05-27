package com.example.fella.tdm1_project;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by RIMA on 15/04/2017.
 */

public class NumeroUrgence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Numéros d'urgence");
        setContentView(R.layout.numero_urgent);

        final ContactUrgence[] lesContactUrgences = new ContactUrgence[]{
                new ContactUrgence("Appeler la Police", "17"),
                new ContactUrgence("Appeler la Gendarmerie","1055"),
                new ContactUrgence("Appeler la Protection Civile","14"),
                new ContactUrgence("Appeler la cellule Anti-terroriste","1548"),
                new ContactUrgence("Appeler le Service Assistance","0556901028")
        };

        //ListView listView = getListView();
        ListView listView = (ListView)findViewById(R.id.list);

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for(int i = 0 ; i < lesContactUrgences.length; ++i) {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("Nom", lesContactUrgences[i].getNom());
            datum.put("Numero", lesContactUrgences[i].getNum());
            data.add(datum);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] {"Nom", "Numero"},
                new int[] {android.R.id.text1,
                        android.R.id.text2});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + lesContactUrgences[arg2].getNum()));
                startActivity(callIntent);

            }


        });



    }

}
