package com.example.fella.tdm1_project;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by Fella on 06/04/2017.
 */

public class DeclarerSinistre extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().show();
        setTitle("Déclarer un sinistre");
        setContentView(R.layout.fragment_declare_sinistre);
         //<< this


    }



    void chargerTypeAccident(View view) {
        if (view == (View) findViewById(R.id.button4)) {
            ViewPagerActivity();

        }
    }

    private void ViewPagerActivity() {
        Intent intent = new Intent(this, TypesAccident.class);
        startActivity(intent);


    }
}
