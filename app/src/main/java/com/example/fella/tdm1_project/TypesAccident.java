package com.example.fella.tdm1_project;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class TypesAccident extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Type de l'accident");
        setContentView(R.layout.types_accident);

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
                Intent intent = new Intent(TypesAccident.super.getApplication(), MainActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }

    public void chargerActivity(View view){
        Intent intent=null;
        intent = new Intent(this, Partie1Declaration.class);

        switch (view.getId())
        {
            case R.id.button5:
                MainActivity.dossier.setTypeAccident("Collision");
                break;

            case R.id.button6 :
                MainActivity.dossier.setTypeAccident("Vol ou cambriolage");
                break;
            case R.id.button7 :
                MainActivity.dossier.setTypeAccident("Incendie ou explosion");
                break;
            case R.id.button8 :
                MainActivity.dossier.setTypeAccident("Brise de glace ou vitre");
                break;        }
        startActivity(intent);


    }



}
