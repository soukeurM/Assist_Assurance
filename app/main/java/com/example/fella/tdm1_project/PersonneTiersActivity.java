package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import static android.R.attr.id;

/**
 * Created by Fella on 16/04/2017.
 */

public class PersonneTiersActivity extends ActionBarActivity {

    private EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;
    private  EditText v7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Personne tiers");
        setContentView(R.layout.fragment_personne_tiers);

        v2 = (EditText) findViewById(R.id.PrenomA);
        v1 = (EditText) findViewById(R.id.NomA);
        v3 = (EditText) findViewById(R.id.AdresseA);
        v4 = (EditText) findViewById(R.id.EmailA);
        v5 = (EditText) findViewById(R.id.Con);
        v6 = (EditText) findViewById(R.id.permisc);
        v7 = (EditText) findViewById(R.id.TelA);
    }



    public void chargerActivity(View view){
        Intent i = getIntent();
        Vehicule vi = (Vehicule) i.getParcelableExtra("VehiculeAccidenté");
        InfoDetaille diV = (InfoDetaille) i.getSerializableExtra("detailAccident");

        Intent intent=null;
        switch (view.getId()) {

            case R.id.butSuiv6:
                personne pers =new personne(v1.getText().toString(),v2.getText().toString(),v3.getText().toString(),v4.getText().toString(),Integer.parseInt(v5.getText().toString()),Integer.parseInt(v6.getText().toString()),Integer.parseInt(v6.getText().toString()));
                intent = new Intent(this, DegatsApparents.class);
                intent.putExtra("VehiculeAccidenté", vi);
                intent.putExtra("detailAccident", diV);
                intent.putExtra("PersonneTiers", pers);
                intent.putExtra("typeAccident", 1);

                break;
        }
        startActivity(intent);
    }



}
