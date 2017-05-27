package com.example.fella.tdm1_project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Fella on 14/04/2017.
 */

public class Profile_activity extends AppCompatActivity implements  TextWatcher, RadioGroup.OnCheckedChangeListener,View.OnClickListener{

    private int nbrChampsRempli=0;
    private boolean champsDejasRemplis=false;
    private Button buttonValider;

    DatabaseReference users;

    EditText v1 ;
    EditText v2 ;
    EditText v3 ;
    EditText v4 ;
    EditText v5 ;
    EditText v6 ;
    EditText v7 ;


    String userName;
    static Assure pers=new Assure();
    RadioGroup radioGroup;
    RadioButton rEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setContentView(R.layout.profil);

        initViews();




        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {


                  pers=snapshot.getValue(Assure.class);
                  // Toast.makeText(Profile_activity.this,infoUser.toString(),Toast.LENGTH_SHORT).show();
                v1.setText(pers.getNom());
                v2.setText(pers.getPrenom());
                v3.setText(pers.getAdresse());
                v4.setText(pers.getNumTel());
                v5.setText(pers.getEmail());
                v6.setText(pers.getInfosPermis().numPermis);
                v7.setText(pers.getMdp());

                switch (pers.getInfosPermis().typePermis)
                {
                    case "B":
                        radioGroup.check(R.id.CB);
                        break;
                    case "A":
                        radioGroup.check(R.id.CA);
                        break;
                    case "C":
                        radioGroup.check(R.id.CC);
                        break;
                    case "D":
                        radioGroup.check(R.id.CD);
                        break;
                    case "E":
                        radioGroup.check(R.id.CE);
                        break;
                    case "F":
                        radioGroup.check(R.id.CF);
                        break;




                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void initViews() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userName=sharedPreferences.getString("username","null");

        setTitle("Mon profil "+userName);


        users = MainActivity.mDatabase.child("utilisateurs").child(userName);

        radioGroup = (RadioGroup) findViewById(R.id.radioG);
        radioGroup.setOnCheckedChangeListener(this);

        buttonValider = (Button) findViewById(R.id.buttonValider);


        v1 = (EditText) findViewById(R.id.NomA);
        v2 = (EditText) findViewById(R.id.PrenomA);
        v3 = (EditText) findViewById(R.id.AdresseA);
        v4 = (EditText) findViewById(R.id.TelA);
        v5 = (EditText) findViewById(R.id.EmailA);
        v6 = (EditText) findViewById(R.id.permisc);
        v7 = (EditText) findViewById(R.id.mdpA);



        v1.addTextChangedListener(this);
        v2.addTextChangedListener(this);
        v3.addTextChangedListener(this);
        v4.addTextChangedListener(this);
        v5.addTextChangedListener(this);
        v6.addTextChangedListener(this);
        v7.addTextChangedListener(this);


        buttonValider.setEnabled(false);
        buttonValider.setOnClickListener(this);
        buttonValider.setBackgroundColor(buttonValider.getContext().getResources().getColor(R.color.gris));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub
        rEnCours=(RadioButton)findViewById(checkedId);
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


            if(nbrChampsRempli==7)
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


    @Override
    public void onClick(View v) {
        if(v==buttonValider)
        {
            Assure infoA= new Assure(v1.getText().toString(),v2.getText().toString(),v3.getText().toString(),v5.getText().toString(),v6.getText().toString(),rEnCours.getText().toString(),v4.getText().toString(),v7.getText().toString());
            users.setValue(infoA);

        }
    }
}
