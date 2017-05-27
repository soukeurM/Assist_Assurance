package com.example.fella.tdm1_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private String modifV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Ajouter un nouveau véhicule");
        setContentView(R.layout.fragment_ip_vehicule);


        buttonValider = (Button) findViewById(R.id.validerAdd);

        position=-1;

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


        Bundle bund = getIntent().getExtras();
        modifV = bund.getString("From","");

        if (modifV.equals("ListVehiculeForModifi")) {

            position= bund.getInt("positionV");
            Intent i = getIntent();
            Vehicule vi = (Vehicule) i.getParcelableExtra("vehiculeModif");

            int partie3matricule= (int) vi.getMatricule()%100;
            int partie2matricule= (int) ((vi.getMatricule()/100)%1000);
            int partie1matricule= (int) vi.getMatricule()/100000;

            v1.setText(vi.getMarque());
            v2.setText(vi.getModele());
            v3.setText(Integer.toString(vi.getContrat()));
            v4.setText(Integer.toString(partie1matricule));
            v5.setText(Integer.toString(partie2matricule));
            v6.setText(Integer.toString(partie3matricule));
        }
        else {
            buttonValider.setEnabled(false);
            buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));
        }




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
                buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.colorAccent));

            }
        } else {
            nbrChampsRempli--;
            buttonValider.setEnabled(false);
            buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));

        }
    }


    public void ReponseAjout(View view) {

        if (view == findViewById(R.id.validerAdd)) {
            Random rn =new Random();
            int id= rn.nextInt()% 20;
            Vehicule vec = new Vehicule(id,v1.getText().toString(),v2.getText().toString(),Integer.parseInt(v3.getText().toString()),Long.parseLong( v4.getText().toString()+v5.getText().toString()+v6.getText().toString()));
            Intent intent = new Intent(this, liste_vehicule_activity.class);
            intent.putExtra("sampleObject", vec);
            intent.putExtra("nouveauV", "true");
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }




}
