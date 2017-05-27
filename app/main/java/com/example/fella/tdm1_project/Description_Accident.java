package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Fella on 16/04/2017.
 */

public class Description_Accident extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Déscription");
        setContentView(R.layout.fragment_ip_observations);

    }


    public void chargerActivity(View view){
        Intent i = getIntent();
        Vehicule vi = (Vehicule) i.getParcelableExtra("VehiculeAccidenté");
        int typeAccident= getIntent().getExtras().getInt("typeAccident");
        InfoDetaille diV = (InfoDetaille) i.getSerializableExtra("detailAccident");
        personne pers = (personne) i.getSerializableExtra("PersonneTiers");

        Intent intent=null;
        switch (view.getId()) {

            case R.id.butSuiv5:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "df_tata@esi.dz" });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "Déclaration Sinistre");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                        "Informations de l'assuré" +
                                "Nom: Soukeur  " +
                                "Prénom: Meryem  " +
                                "Adresse: 43 chemin AEK ALger  " +
                                "Numéro de Tel.: 0555 09 09 96  " +
                                "Email: dm_soukeur@esi.dz  " +
                                "" +
                                "Vehicule accidenté:" +
                                "Numero de police: "+vi.getContrat()+"  " +
                                "Numero d'immatriculation"+vi.getMatricule()+"  " +
                                "Marque"+vi.getMarque()+"  " +
                                "Modèle: "+vi.getModele()+"  " +
                                "" +
                                "Détails de l'accident: " +
                                "Date: "+diV.getDateAccident()+"  " +
                                "Heure: "+diV.getHeureAccident()+"  " +
                                "Lieu: "+diV.getLieuS()+"  "
                                );
                emailIntent.setType("image/png");
                /*
                Uri myUri = Uri.parse("file://" + path);
                emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                */
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                break;
        }
        startActivity(intent);
    }



}
