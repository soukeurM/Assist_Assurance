package com.example.fella.tdm1_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.view.View.Y;


/**
 * Created by Fella on 15/04/2017.
 */

public class Vehiclue_Activity  extends ActionBarActivity implements TextWatcher{

    private int nbrChampsRempli=0;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;
    int position;

    private  EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;

    DatabaseReference vehicules ;

    DatabaseReference mDatabase;
    String userName;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Ajouter un nouveau véhicule");
        setContentView(R.layout.fragment_ip_vehicule);

        iniViews();

    }

    private void iniViews() {

        position=-1;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        vehicules  = mDatabase.child("vehicules").push();

        buttonValider = (Button) findViewById(R.id.validerAdd);

        v2 = (EditText) findViewById(R.id.ModeleV);
        v1 = (EditText) findViewById(R.id.MarqueV);
        v3 = (EditText) findViewById(R.id.NumContrat);
        v4 = (EditText) findViewById(R.id.NumV1);
        v5 = (EditText) findViewById(R.id.NumV2);
        v6 = (EditText) findViewById(R.id.NumV3);


        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v3.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);
        v6.addTextChangedListener(this);

        buttonValider.setEnabled(false);
        buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));

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
                buttonValider.setBackgroundResource(R.drawable.normal_button);

            }
        } else {
            nbrChampsRempli--;
            buttonValider.setEnabled(false);
            buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));

        }
    }


    public void ReponseAjout(View view) {

        if (view == findViewById(R.id.validerAdd)) {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            userName = sharedPreferences.getString("username", "null");
            vehicules.setValue(new Vehicule(v1.getText().toString(),v2.getText().toString(),v3.getText().toString(),v4.getText().toString()+v5.getText().toString()+v6.getText().toString(),userName));
            Toast.makeText(Vehiclue_Activity.this,"Nouveau véhicule ajouté",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, liste_vehicule_activity.class);
            startActivity(intent);
        }
    }




}
