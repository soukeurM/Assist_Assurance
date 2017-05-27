package com.example.fella.tdm1_project;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Partie1Declaration extends ActionBarActivity  implements TextWatcher {
    private Spinner spinner;
    private  EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;
    private int nbrChampsRempli=6;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;
    ArrayAdapter<Vehicule> adapter;
    private DossierDAO dossierDAO;
    int position;
    private ListView mCompleteListView;
    private ArrayList<Vehicule> mItems;
    String userName;
    DatabaseReference vehiculesListe;

    static Vehicule vehicule=new Vehicule();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Véhicule accidenté");
        setContentView(R.layout.partie1_declaration);

        dossierDAO = new DossierDAO(this);
        vehiculesListe = MainActivity.mDatabase.child("vehicules");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName=sharedPreferences.getString("username","null");

        initViews();
        ValueEventListener valueEventListener = vehiculesListe.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mItems.clear();
                for (DataSnapshot vehiculeItem : snapshot.getChildren()) {

//                    if(vehiculeItem.child("propriétaire").getValue().equals(userName))
                    if(vehiculeItem.child("propriétaire").getValue().equals(userName))
                    {
                        vehicule =new Vehicule();
                        vehicule= (vehiculeItem.getValue(Vehicule.class));
                        vehicule.setIdVehicule(vehiculeItem.getKey().toString());

                        mItems.add(vehicule);

                    }

                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//-------------------------------------------------------------------------------------------------


        spinner = (Spinner) findViewById(R.id.VehiculeL);
        adapter =
                new ArrayAdapter<Vehicule>(getApplicationContext(),R.layout.chpspinner, mItems);
        adapter.setDropDownViewResource(R.layout.chpspinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                whenSelect(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });





        v2 = (EditText) findViewById(R.id.ModeleV);
        v1 = (EditText) findViewById(R.id.MarqueV);
        v3 = (EditText) findViewById(R.id.NumContrat);
        v4 = (EditText) findViewById(R.id.NumV1);
        v5 = (EditText) findViewById(R.id.NumV2);
        v6 = (EditText) findViewById(R.id.NumV3);

        v1.setEnabled(false);
        v2.setEnabled(false);
        v3.setEnabled(false);
        v4.setEnabled(false);
        v5.setEnabled(false);
        v6.setEnabled(false);

        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v3.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);
        v6.addTextChangedListener(this);

        buttonValider = (Button) findViewById(R.id.butSuiv1);



    }

    public void whenSelect(int position){
        Vehicule vehicule = mItems.get(position);

        v1.setText(vehicule.getMarque());
        v2.setText(vehicule.getModele());
        v3.setText(vehicule.getNumContrat());
        String matricule = vehicule.getMatricule();
        v4.setText(matricule.substring(0, matricule.length()-5));
        v5.setText(matricule.substring(matricule.length()-5, matricule.length()-2));
        v6.setText(matricule.substring(matricule.length()-2, matricule.length()));
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (s.toString().trim().length() > 0) {
            champsDejasRemplis=true;
        }
        else
        {
            champsDejasRemplis=false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {



    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().trim().length() > 0) {

            if (champsDejasRemplis==false) {nbrChampsRempli++;}

            if(nbrChampsRempli==6)
            {
                buttonValider.setEnabled(true);

            }
        } else {
            nbrChampsRempli--;

            buttonValider.setEnabled(false);

        }
    }


    public void chargerActivity(View view){
        Intent intent=null;
        switch (view.getId()) {


            case R.id.butSuiv1:
                intent = new Intent(this, DetailsAccident.class);
                //Vehicule vec = new Vehicule(1,v1.getText().toString(),v2.getText().toString(),Integer.parseInt(v3.getText().toString()),Long.parseLong( v4.getText().toString()+v5.getText().toString()+v6.getText().toString()));
                MainActivity.dossier.setMatricule(v4.getText().toString()+v5.getText().toString()+v6.getText().toString());
                break;
        }
        startActivity(intent);
    }

    private void initViews() {

        position=-1;
        mItems = new ArrayList<Vehicule>();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.icon_home: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alert!");
                builder.setMessage("Voulez-vous enregistrer votre déclaration?");

                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.dossier.setEtat("Ouvert");

                        dossierDAO.ajouter(MainActivity.dossier);

                        Intent intent = new Intent(Partie1Declaration.super.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Partie1Declaration.super.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        }
        return false;
    }


}