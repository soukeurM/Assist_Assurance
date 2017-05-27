package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.TextWatcher;

import java.util.Calendar;

/**
 * Created by Fella on 14/04/2017.
 */

public class Profile_activity extends AppCompatActivity implements  TextWatcher, RadioGroup.OnCheckedChangeListener{

    private int nbrChampsRempli=0;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Mon profil");
        setContentView(R.layout.profil);

        // Rechercher l'instance du radio Group, nous en aurons besoin pour poser un listener
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioG);

        // On pose notre listener sur le radio group
        radioGroup.setOnCheckedChangeListener(this);

        buttonValider = (Button) findViewById(R.id.buttonValider);


        EditText v1 = (EditText) findViewById(R.id.NomA);
        EditText v2 = (EditText) findViewById(R.id.PrenomA);
        EditText v3 = (EditText) findViewById(R.id.AdresseA);
        EditText v4 = (EditText) findViewById(R.id.TelA);
        EditText v5 = (EditText) findViewById(R.id.EmailA);

        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v3.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);

        buttonValider.setEnabled(false);
        buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        RadioButton rEnCours=(RadioButton)findViewById(checkedId);



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
                buttonValider.setEnabled(true);
                buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.colorAccent));


            }
        } else {
            nbrChampsRempli--;
            buttonValider.setEnabled(false);
            buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));


        }


    }


    //if (v1.getText().toString().trim().equals("")|| v2.getText().toString().trim().equals("")|| v3.getText().toString().trim().equals("")||v4.getText().toString().trim().equals("")||v5.getText().toString().trim().equals(""))



}
