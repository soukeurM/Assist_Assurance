package com.example.fella.tdm1_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fella.tdm1_project.DbSqlLight.DossierDAO;

/**
 * Created by RIMA on 21/05/2017.
 */

public class InfosTiersActivity extends AppCompatActivity {
    EditText txtNom, txtPrenom, txtAdresse, txtEmail, txtCon, txtPermis, txtTel;
    Button btnModif;
    private DossierDAO dossierDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Informations de la personne tiers");
        setContentView(R.layout.fragment_personne_tiers);

        dossierDAO = new DossierDAO(this);

        final String unDossier = (String) getIntent().getSerializableExtra("noDossier");
        final String unEtat = (String) getIntent().getSerializableExtra("unEtat");
        final String unType = (String) getIntent().getSerializableExtra("typeDossier");

        btnModif = (Button)findViewById(R.id.butSuiv6);
        btnModif.setText("Modifier");
        txtPrenom = (EditText) findViewById(R.id.PrenomA);
        txtNom = (EditText) findViewById(R.id.NomA);
        txtAdresse = (EditText) findViewById(R.id.AdresseA);
        txtEmail = (EditText) findViewById(R.id.EmailA);
        txtCon = (EditText) findViewById(R.id.Con);
        txtPermis = (EditText) findViewById(R.id.permisc);
        txtTel = (EditText) findViewById(R.id.TelA);

        if(DetailsDossier.personneTiers != null){
            txtNom.setText(DetailsDossier.personneTiers.getNom());
            txtPrenom.setText(DetailsDossier.personneTiers.getPrenom());
            txtAdresse.setText(DetailsDossier.personneTiers.getAdresse());
            txtEmail.setText(DetailsDossier.personneTiers.getAssurance());
            txtCon.setText(DetailsDossier.personneTiers.getNumPoliceContrat());
            txtPermis.setText(DetailsDossier.personneTiers.getNumPermis());
            txtTel.setText(DetailsDossier.personneTiers.getNumTel());
        }


        btnModif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(unEtat.equals("Ouvert")){
                    PersonneTiers pers = new PersonneTiers(txtNom.getText().toString(), txtPrenom.getText().toString(),
                            txtAdresse.getText().toString(), txtEmail.getText().toString(), txtCon.getText().toString(),
                            txtPermis.getText().toString(), txtTel.getText().toString());

                    Dossier value = dossierDAO.selectionner(unDossier);
                    value.setInfoTiers(pers);
                    dossierDAO.modifier(value);

                    Intent intent = new Intent(getApplicationContext(), DetailsDossier.class);
                    intent.putExtra("noDossier", unDossier);
                    intent.putExtra("unEtat", unEtat);
                    intent.putExtra("unEtat", unEtat);
                    startActivity(intent);
                }else{
                    Toast.makeText(InfosTiersActivity.this, "Il n'est possible de modifier un dossier que s'il est ouvert", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
