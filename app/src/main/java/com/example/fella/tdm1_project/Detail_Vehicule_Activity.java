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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.view.View.Y;


/**
 * Created by Fella on 15/04/2017.
 */

public class Detail_Vehicule_Activity extends ActionBarActivity implements TextWatcher{

    private int nbrChampsRempli=0;
    private boolean champsDejasRemplis=false;
    private Button buttonModifier;
    int position;

    private EditText v1;
    private  EditText v2;
    private  EditText v3;
    private  EditText v4;
    private  EditText v5;
    private  EditText v6;
    Vehicule vi = new Vehicule();

    DatabaseReference vehiculeM ;

    DatabaseReference mDatabase;
    String userName;

    long nbrVehicules=-1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Détails du véhicule");
        setContentView(R.layout.activity_detail__vehicule_);


        initViews();


    }

    private void initViews() {

        position=-1;

        v2 = (EditText) findViewById(R.id.ModeleV);
        v1 = (EditText) findViewById(R.id.MarqueV);
        v3 = (EditText) findViewById(R.id.NumContrat);
        v4 = (EditText) findViewById(R.id.NumV1);
        v5 = (EditText) findViewById(R.id.NumV2);
        v6 = (EditText) findViewById(R.id.NumV3);


        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);
        v6.addTextChangedListener(this);

        buttonModifier=(Button) findViewById(R.id.validerModif);
        buttonModifier.setBackgroundResource(R.drawable.normal_button);




        Bundle bund = getIntent().getExtras();
        position= bund.getInt("positionV");
        Intent i = getIntent();
        vi = (Vehicule) i.getParcelableExtra("vehiculeModif");




//        int partie3matricule= Integer.parseInt(vi.getMatricule())%100;
//        int partie2matricule= (Integer.parseInt(vi.getMatricule())/100)%1000;
//        int partie1matricule= Integer.parseInt(vi.getMatricule())/100000;

        v1.setText(vi.getMarque());
        v2.setText(vi.getModele());
        v3.setText(vi.getNumContrat());
        String matricule = vi.getMatricule();
        v4.setText(matricule.substring(0, matricule.length()-5));
        v5.setText(matricule.substring(matricule.length()-5, matricule.length()-2));
        v6.setText(matricule.substring(matricule.length()-2, matricule.length()));
//        v4.setText(Integer.toString(partie1matricule));
//        v5.setText(Integer.toString(partie2matricule));
//        v6.setText(Integer.toString(partie3matricule));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName = sharedPreferences.getString("username", "null");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        vehiculeM  = mDatabase.child("vehicules").child(vi.getIdVehicule());
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

            if(nbrChampsRempli==5)
            {
                buttonModifier.setEnabled(true);
                buttonModifier.setBackgroundResource(R.drawable.normal_button);

            }
        } else {
            nbrChampsRempli--;
            buttonModifier.setEnabled(false);
            buttonModifier.setBackgroundColor(buttonModifier.getContext().getResources().getColor(R.color.gris));

        }
    }


    public void ReponseAjout(View view) {
        vehiculeM.setValue(new Vehicule(v1.getText().toString(),v2.getText().toString(),v3.getText().toString(),v4.getText().toString()+v5.getText().toString()+v6.getText().toString(),userName));
        Toast.makeText(Detail_Vehicule_Activity.this,"Véhicule modifié",Toast.LENGTH_SHORT).show();
        onBackPressed();
        }




}
